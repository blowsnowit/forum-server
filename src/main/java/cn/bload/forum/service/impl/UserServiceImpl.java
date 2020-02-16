package cn.bload.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.apache.ibatis.transaction.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.bload.forum.constenum.MailTemplate;
import cn.bload.forum.dao.UserMapper;
import cn.bload.forum.entity.dto.ArticleUserDTO;
import cn.bload.forum.entity.dto.UserDTO;
import cn.bload.forum.entity.query.UserQuery;
import cn.bload.forum.entity.vo.UserFindVO;
import cn.bload.forum.entity.vo.UserLoginVO;
import cn.bload.forum.entity.vo.UserRegisterVO;
import cn.bload.forum.entity.vo.UserUpdateEmailVO;
import cn.bload.forum.entity.vo.UserUpdatePasswordVO;
import cn.bload.forum.entity.vo.UserUpdateVO;
import cn.bload.forum.exception.MyRuntimeException;
import cn.bload.forum.model.User;
import cn.bload.forum.service.MailService;
import cn.bload.forum.service.RedisService;
import cn.bload.forum.service.UserNotifyService;
import cn.bload.forum.service.UserService;
import cn.bload.forum.utils.PasswordUtil;
import lombok.extern.log4j.Log4j2;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author blowsnow
 * @since 2020-01-18
 */
@Service("userService")
@Log4j2
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserMapper userMapper;
    @Autowired
    MailService mailService;
    @Autowired
    UserNotifyService userNotifyService;
    @Autowired
    RedisService redisService;

    @Override
    public User findByUserName(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUserName,userName);
        return userMapper.selectOne(queryWrapper);
    }

    public User findByUserEmail(String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUserEmail,email);
        return userMapper.selectOne(queryWrapper);
    }



    @Override
    public UserDTO login(UserLoginVO userLoginVO) throws MyRuntimeException {
        User user =  findByUserName(userLoginVO.getUserName());
        if (user == null){
            throw new MyRuntimeException("账号不存在");
        }
        String saltPassowrd = PasswordUtil.encodePassword(userLoginVO.getPassword());
        if (!user.getUserPassword().equals(saltPassowrd)){
            throw new MyRuntimeException("密码错误");
        }
        //验证用户状态
        if (user.getUserStatus() == 0){
            throw new MyRuntimeException("账号已被禁用");
        }
        UserDTO userDTO = user.toUserDTO();
        return userDTO;
    }

    @Override
    public void register(UserRegisterVO userRegisterVO) {
        User user =  findByUserName(userRegisterVO.getUserName());
        if (user != null){
            throw new MyRuntimeException("账号已存在");
        }
        //验证邮箱是否存在
        user = findByUserEmail(userRegisterVO.getEmail());
        if (user != null){
            throw new MyRuntimeException("邮箱已存在");
        }

        //注册对象
        User registerUser = new User();
        String saltPassowrd = PasswordUtil.encodePassword(userRegisterVO.getPassword());
        registerUser.setUserPassword(saltPassowrd);
        registerUser.setUserName(userRegisterVO.getUserName());
        registerUser.setUserNick(userRegisterVO.getUserName());
        registerUser.setUserAddTime(new Date());
        registerUser.setUserEmail(userRegisterVO.getEmail());

        int result = userMapper.insert(registerUser);
        if (result <= 0){
            throw new MyRuntimeException("注册失败");
        }

        //队列发送注册成功邮件
        mailService.sendTemplate(userRegisterVO.getEmail(), MailTemplate.REGISTER_SUCCESS,null);
    }

    @Override
    public UserDTO findPasswordByEmail(UserFindVO userFindVO) {
        User user = findByUserEmail(userFindVO.getEmail());
        if (user == null){
            throw new MyRuntimeException("该邮箱未在本站注册");
        }
        User newUser = new User();
        newUser.setUserId(user.getUserId());
        String saltPassowrd = PasswordUtil.encodePassword(userFindVO.getPassword());
        newUser.setUserPassword(saltPassowrd);
        userMapper.updateById(newUser);
        return user.toUserDTO();
    }

    @Override
    public ArticleUserDTO getUserInfo(Integer userId) {
        User user = userMapper.selectById(userId);
        if (user == null){
            return null;
        }
        ArticleUserDTO articleUserDTO = user.toArticleUserDTO();
        articleUserDTO.setIsOnline(redisService.getUserOnline(userId));
        return articleUserDTO;
    }

    @Override
    public void updateUserInfo(User user) {
        if (user.getUserId() == null){
            throw new MyRuntimeException("用户id不存在");
        }
        if (user.getUserPassword() != null){
            String saltPassowrd = PasswordUtil.encodePassword(user.getUserPassword());
            user.setUserPassword(saltPassowrd);
        }
        int result = userMapper.updateById(user);
        if (result != 1){
            throw new MyRuntimeException("修改失败");
        }
    }

    @Override
    public void updateUserInfo(Integer userId, UserUpdateVO userUpdateVO) {
        User user = userUpdateVO.toUser();
        user.setUserId(userId);
        int result =  userMapper.updateById(user);
        if (result != 1){
            throw new TransactionException("更新失败");
        }
    }

    @Override
    public void updateUserPasswordCheck(Integer userId, UserUpdatePasswordVO userUpdatePasswordVO) {
        if (!userUpdatePasswordVO.getNewUserPassword().equals(userUpdatePasswordVO.getConfirmNewUserPassword())){
            throw new MyRuntimeException("两次新密码不一致");
        }

        //验证旧密码是否正确
        String oldSaltPassowrd = PasswordUtil.encodePassword(userUpdatePasswordVO.getOldUserPassword());
        User user = userMapper.selectById(userId);
        if (!user.getUserPassword().equals(oldSaltPassowrd)){
            throw new MyRuntimeException("旧密码不正确");
        }

        User newUser = new User();
        newUser.setUserId(userId);
        newUser.setUserPassword(userUpdatePasswordVO.getNewUserPassword());
        updateUserInfo(newUser);
    }

    @Override
    public void updateUserEmailCheck(Integer userId, UserUpdateEmailVO userUpdateEmailVO) {
        //验证旧密码是否正确
        String oldSaltPassowrd = PasswordUtil.encodePassword(userUpdateEmailVO.getOldUserPassword());
        User user = userMapper.selectById(userId);
        if (!user.getUserPassword().equals(oldSaltPassowrd)){
            throw new MyRuntimeException("旧密码不正确");
        }
        if (user.getUserEmail().equals(userUpdateEmailVO.getEmail())){
            throw new MyRuntimeException("旧邮箱和新邮箱是同一个");
        }
        //验证邮箱是否存在
        user = findByUserEmail(userUpdateEmailVO.getEmail());
        if (user != null){
            throw new MyRuntimeException("邮箱已存在");
        }
        User newUser = new User();
        newUser.setUserId(userId);
        newUser.setUserEmail(userUpdateEmailVO.getEmail());

        updateUserInfo(newUser);
    }

    @Override
    @Cacheable(value = "cache_op",key = "'userid_' + #userId")
    public boolean isOp(Integer userId) {
        User user = userMapper.selectById(userId);
        return user.getUserOp() == 1;
    }

    @Override
    public List<User> getUserList(UserQuery userQuery) {
        return userMapper.getUserList(userQuery);
    }

    @Override
    public void saveUserStatus(Integer userId, Integer userStatus) {
        User user = new User();
        user.setUserId(userId);
        user.setUserStatus(userStatus);

        updateUserInfo(user);
    }

}
