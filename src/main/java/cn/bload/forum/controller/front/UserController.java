package cn.bload.forum.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import cn.bload.forum.base.BaseController;
import cn.bload.forum.base.ResultBean;
import cn.bload.forum.constenum.MailTemplate;
import cn.bload.forum.entity.dto.ArticleUserDTO;
import cn.bload.forum.entity.dto.UserDTO;
import cn.bload.forum.entity.vo.UserEmailVO;
import cn.bload.forum.entity.vo.UserLoginVO;
import cn.bload.forum.entity.vo.UserRegisterVO;
import cn.bload.forum.entity.vo.UserUpdateEmailVO;
import cn.bload.forum.entity.vo.UserUpdatePasswordVO;
import cn.bload.forum.entity.vo.UserUpdateVO;
import cn.bload.forum.exception.MyRuntimeException;
import cn.bload.forum.service.MailService;
import cn.bload.forum.service.UserService;
import cn.bload.forum.utils.ResultGenerator;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.ICaptcha;
import cn.hutool.core.util.RandomUtil;
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
    @Autowired
    MailService mailService;

    /**
     * 检查验证码
     * TODO 限制检查次数和时间
     * @param token 图形验证码token
     * @param code 验证码
     */
    protected void checkCaptch(String token,String code){
        Object cacheCode = redisService.getCaptch(token);
        if (cacheCode == null){
            throw new MyRuntimeException("请重新获取图形验证码");
        }
        if (!code.equals(cacheCode.toString())){
            throw new MyRuntimeException("图形验证码错误");
        }
    }

    @GetMapping(value = "/captch")
    @ApiOperation(value = "/captch", notes = "验证码")
    public ResultBean captch(HttpServletResponse response){
        ICaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 40);
        byte[] imageBytes = ((CircleCaptcha) captcha).getImageBytes();

        Map<String, Object> map = new HashMap<>();
        String token = UUID.randomUUID().toString();
        String code = captcha.getCode();
        redisService.cacheCaptch(token,code);

        map.put("token",token);
        map.put("captch",imageBytes);
        return ResultGenerator.getSuccessResult(map);
    }

    /**
     * 验证邮箱验证码
     * TODO 限制检查次数和时间
     * TODO 考虑是否只能使用一次验证码
     * @param email 邮箱
     * @param code 验证码
     */
    protected void checkEmailCode(String email,String code){
        Object cahceCode = redisService.getEmailCode(email);
        if (cahceCode == null){
            throw new MyRuntimeException("请重新获取邮箱验证码");
        }
        if (!code.equals(cahceCode.toString())){
            throw new MyRuntimeException("邮箱验证码错误");
        }
    }

    @PostMapping(value = "/sendEmailCode")
    @ApiOperation(value = "/sendEmailCode", notes = "发送用户注册邮箱验证码")
    public ResultBean sendEmailCode(@RequestBody UserEmailVO userEmailVO){
        checkCaptch(userEmailVO.getToken(),userEmailVO.getCode());

        String code = RandomUtil.randomNumbers(6);
        //缓存邮箱验证码  邮箱key，code
        redisService.cacheEmailCode(userEmailVO.getEmail(),code);

        Map<String, Object> params = new HashMap<>();
        params.put("code",code);

        MailTemplate mailTemplate = MailTemplate.getByTemplateName(userEmailVO.getTemplateName());
        mailService.sendTemplate(userEmailVO.getEmail(), mailTemplate,params);

        return ResultGenerator.getSuccessResult("发送成功");
    }



    @PostMapping(value = "/login")
    @ApiOperation(value = "/login", notes = "用户登录")
    public ResultBean<UserDTO> login(@Valid @RequestBody UserLoginVO userLoginVO){
        UserDTO userDTO = userService.login(userLoginVO);
        return ResultGenerator.getSuccessResult(userDTO);
    }

    @PostMapping(value = "/register")
    @ApiOperation(value = "/register", notes = "用户注册")
    public ResultBean register(@RequestBody UserRegisterVO userRegisterVO){
        //效验邮箱验证码
        checkEmailCode(userRegisterVO.getEmail(),userRegisterVO.getEmailCode());

        if (!userRegisterVO.getPassword().equals(userRegisterVO.getConfirmPassword())){
            return ResultGenerator.getErrorResult("两次密码不正确");
        }
        userService.register(userRegisterVO);
        return ResultGenerator.getSuccessResult("注册成功");
    }





    @GetMapping(value = "/{userId}")
    @ApiOperation(value = "/{userId}", notes = "获取指定用户信息")
    public ResultBean getUserInfo(@ApiParam(value = "用户id") @PathVariable Integer userId){
        ArticleUserDTO userDTO = userService.getUserInfo(userId);
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
