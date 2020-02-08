package cn.bload.forum.service;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.bload.forum.entity.dto.ArticleUserDTO;
import cn.bload.forum.entity.dto.UserDTO;
import cn.bload.forum.entity.vo.UserLoginVO;
import cn.bload.forum.entity.vo.UserRegisterVO;
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
     * 获取指定用户信息
     * @param userId 用户id
     * @return
     */
    ArticleUserDTO getUserInfo(Integer userId);

    /**
     * 更新用户信息
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

}
