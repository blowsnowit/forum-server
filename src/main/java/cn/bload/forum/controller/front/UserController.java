package cn.bload.forum.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import cn.bload.forum.base.BaseController;
import cn.bload.forum.base.ResultBean;
import cn.bload.forum.entity.dto.ArticleUserDTO;
import cn.bload.forum.entity.dto.UserDTO;
import cn.bload.forum.entity.vo.UserFindVO;
import cn.bload.forum.entity.vo.UserLoginVO;
import cn.bload.forum.entity.vo.UserRegisterVO;
import cn.bload.forum.entity.vo.UserUpdateEmailVO;
import cn.bload.forum.entity.vo.UserUpdatePasswordVO;
import cn.bload.forum.entity.vo.UserUpdateVO;
import cn.bload.forum.service.UserService;
import cn.bload.forum.utils.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/1/18 11:28
 * @describe 类描述:
 */
@RestController
@RequestMapping("/api/user")
@Api(value = "/api/user",tags = "用户控制器")
public class UserController extends BaseController {
    @Autowired
    UserService userService;


    @PostMapping(value = "/login")
    @ApiOperation(value = "/login", notes = "用户登录")
    public ResultBean<UserDTO> login(@Valid @RequestBody UserLoginVO userLoginVO){
        UserDTO userDTO = userService.login(userLoginVO);

        userNotifyService.pushUserLoginNotify(request,userDTO);
        return ResultGenerator.getSuccessResult(userDTO);
    }

    @PostMapping(value = "/register")
    @ApiOperation(value = "/register", notes = "用户注册")
    public ResultBean register(@Validated @RequestBody UserRegisterVO userRegisterVO){
        //效验邮箱验证码
        checkEmailCode(userRegisterVO.getEmail(),userRegisterVO.getEmailCode());

        if (!userRegisterVO.getPassword().equals(userRegisterVO.getConfirmPassword())){
            return ResultGenerator.getErrorResult("两次密码不正确");
        }
        userService.register(userRegisterVO);
        return ResultGenerator.getSuccessResult("注册成功");
    }

    @PutMapping(value = "/find")
    @ApiOperation(value = "/find", notes = "找回密码")
    public ResultBean find(@Validated @RequestBody UserFindVO userFindVO){
        //效验邮箱验证码
        checkEmailCode(userFindVO.getEmail(),userFindVO.getEmailCode());

        if (!userFindVO.getPassword().equals(userFindVO.getConfirmPassword())){
            return ResultGenerator.getErrorResult("两次密码不正确");
        }
        UserDTO userDTO = userService.findPasswordByEmail(userFindVO);
        return ResultGenerator.getSuccessResult(userDTO);
    }

    @PutMapping(value = "/logout")
    @ApiOperation(value = "/logout", notes = "登出")
    public ResultBean logout(){
        Integer userId = getUserId();
        redisService.delCacheUserOnline(userId);
        return ResultGenerator.getSuccessResult();
    }




    @GetMapping(value = "/{userId}")
    @ApiOperation(value = "/{userId}", notes = "获取指定用户信息")
    public ResultBean getUserInfo(@ApiParam(value = "用户id") @PathVariable Integer userId){
        ArticleUserDTO userDTO = userService.getUserInfo(userId);
        if (userDTO == null){
            return ResultGenerator.getErrorResult("用户不存在");
        }
        return ResultGenerator.getSuccessResult(userDTO,"获取成功");
    }

    @PutMapping(value = "")
    @ApiOperation(value = "", notes = "更新用户个人资料")
    public ResultBean updateUserInfo(@Valid @RequestBody UserUpdateVO userUpdateVO){
        userService.updateUserInfo(getUserId(),userUpdateVO);
        return ResultGenerator.getSuccessResult("更新成功");
    }


    @PutMapping(value = "/password")
    @ApiOperation(value = "/password", notes = "更新用户密码")
    public ResultBean updateUserPassword(@Valid @RequestBody UserUpdatePasswordVO userUpdatePasswordVO){
        userService.updateUserPasswordCheck(getUserId(),userUpdatePasswordVO);
        return ResultGenerator.getSuccessResult("更新成功");
    }


    @PutMapping(value = "/email")
    @ApiOperation(value = "/email", notes = "更新用户邮箱")
    public ResultBean updateUserEmail(@Valid @RequestBody UserUpdateEmailVO userUpdateEmailVO){
        //验证邮箱验证码
        checkEmailCode(userUpdateEmailVO.getEmail(),userUpdateEmailVO.getEmailCode());

        userService.updateUserEmailCheck(getUserId(),userUpdateEmailVO);
        return ResultGenerator.getSuccessResult("更新成功");
    }
}
