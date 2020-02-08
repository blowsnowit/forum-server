package cn.bload.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.apache.ibatis.transaction.TransactionException;
import org.springframework.stereotype.Service;

import java.util.Date;

import javax.annotation.Resource;

import cn.bload.forum.dao.UserMapper;
import cn.bload.forum.entity.dto.ArticleUserDTO;
import cn.bload.forum.entity.dto.UserDTO;
import cn.bload.forum.entity.vo.UserLoginVO;
import cn.bload.forum.entity.vo.UserRegisterVO;
import cn.bload.forum.entity.vo.UserUpdatePasswordVO;
import cn.bload.forum.entity.vo.UserUpdateVO;
import cn.bload.forum.exception.MyRuntimeException;
import cn.bload.forum.model.User;
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

    @Override
    public User findByUserName(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUserName,userName);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public UserDTO login(UserLoginVO userLoginVO) throws MyRuntimeException {
        User user =  findByUserName(userLoginVO.getUserName());
        if (user == null){
            throw new MyRuntimeException("账号不存在");
        }
        String saltPassowrd = PasswordUtil.encodePassword(userLoginVO.getPassword());
        log.info(saltPassowrd);
        if (!user.getUserPassword().equals(saltPassowrd)){
            throw new MyRuntimeException("密码错误");
        }
        //验证用户状态
        if (user.getUserStatus() == 0){
            throw new MyRuntimeException("账号已被禁用");
        }
        //TODO 队列发送登录邮件

        return user.toUserDTO();
    }

    @Override
    public void register(UserRegisterVO userRegisterVO) {
        User user =  findByUserName(userRegisterVO.getUserName());
        if (user != null){
            throw new MyRuntimeException("账号已存在");
        }
        //注册对象
        User registerUser = new User();
        String saltPassowrd = PasswordUtil.encodePassword(userRegisterVO.getPassword());
        registerUser.setUserPassword(saltPassowrd);
        registerUser.setUserName(userRegisterVO.getUserName());
        registerUser.setUserNick(userRegisterVO.getUserName());
        registerUser.setUserAddTime(new Date());

        int result = userMapper.insert(registerUser);
        if (result <= 0){
            throw new MyRuntimeException("注册失败");
        }

        //TODO 队列发送注册邮件

    }

    @Override
    public ArticleUserDTO getUserInfo(Integer userId) {
        User user = userMapper.selectById(userId);
        return user.toArticleUserDTO();
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
        String saltPassowrd = PasswordUtil.encodePassword(userUpdatePasswordVO.getNewUserPassword());
        User newUser = new User();
        newUser.setUserId(userId);
        newUser.setUserPassword(saltPassowrd);
        userMapper.updateById(newUser);
    }
}
