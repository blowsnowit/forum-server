package cn.bload.forum.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

import cn.bload.forum.entity.dto.ArticleUserDTO;
import cn.bload.forum.entity.dto.UserDTO;
import cn.bload.forum.entity.query.UserQuery;
import cn.bload.forum.entity.vo.UserFindVO;
import cn.bload.forum.entity.vo.UserLoginVO;
import cn.bload.forum.entity.vo.UserRegisterVO;
import cn.bload.forum.entity.vo.UserUpdateEmailVO;
import cn.bload.forum.entity.vo.UserUpdatePasswordVO;
import cn.bload.forum.entity.vo.UserUpdateVO;
import cn.bload.forum.model.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author blowsnow
 * @since 2020-01-18
 */
public interface UserService extends IService<User> {

    User findByUserName(String userName);

    /**
     * 登录
     * @param userLoginVO 登录对象
     * @return
     */
    UserDTO login(UserLoginVO userLoginVO);

    /**
     * 注册
     * @param userRegisterVO 注册对象
     * @return
     */
    void register(UserRegisterVO userRegisterVO);


    /**
     * 通过邮箱找回密码
     * @param userFindVO
     */
    UserDTO findPasswordByEmail(UserFindVO userFindVO);

    /**
     * 获取指定用户信息
     * @param userId 用户id
     * @return
     */
    ArticleUserDTO getUserInfo(Integer userId);


    /**
     * 更新用户信息
     * 自动处理密码加密
     * @param user
     */
    void updateUserInfo(User user);

    /**
     * 更新用户基础资料信息
     * @param userId
     * @param userUpdateVO
     */
    void updateUserInfo(Integer userId, UserUpdateVO userUpdateVO);

    /**
     * 更新用户密码并且检查
     * @param userId
     * @param userUpdatePasswordVO
     */
    void updateUserPasswordCheck(Integer userId, UserUpdatePasswordVO userUpdatePasswordVO);

    /**
     * 更新用户邮箱并且检查
     * @param userId
     * @param userUpdateEmailVO
     */
    void updateUserEmailCheck(Integer userId, UserUpdateEmailVO userUpdateEmailVO);


    /**
     * 判断改用户是否是管理员
     * @param userId 用户id
     * @return
     */
    boolean isOp(Integer userId);

    /**
     * 获取用户列表
     * @param userQuery
     * @return
     */
    List<User> getUserList(UserQuery userQuery);

    /**
     * 修改用户状态
     * @param userId 用户id
     * @param userStatus 用户状态
     */
    void saveUserStatus(Integer userId, Integer userStatus);


}
