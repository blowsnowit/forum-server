package cn.bload.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import cn.bload.forum.annotation.NeedLogin;
import cn.bload.forum.base.BaseController;
import cn.bload.forum.base.ResultBean;
import cn.bload.forum.constenum.MailTemplate;
import cn.bload.forum.entity.vo.UserEmailVO;
import cn.bload.forum.service.MailService;
import cn.bload.forum.utils.ResultGenerator;
import cn.bload.forum.utils.oss.AbstractOss;
import cn.bload.forum.utils.oss.FileOss;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.ICaptcha;
import cn.hutool.core.util.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/12 14:09
 * @describe 类描述:
 */
@RestController
@RequestMapping("/api/common")
@Api(value = "/api/common",tags = "公共控制器")
public class CommonController extends BaseController {
    @Autowired
    MailService mailService;

    @GetMapping(value = "/captch")
    @ApiOperation(value = "/captch", notes = "获取验证码")
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



    @PostMapping(value = "/sendEmailCode")
    @ApiOperation(value = "/sendEmailCode", notes = "发送用户注册邮箱验证码")
    public ResultBean sendEmailCode(@RequestBody UserEmailVO userEmailVO){
        checkCaptch(userEmailVO.getToken(),userEmailVO.getCode());

        String code = RandomUtil.randomNumbers(6);

        Map<String, Object> params = new HashMap<>();
        params.put("code",code);

        MailTemplate mailTemplate = MailTemplate.getByTemplateName(userEmailVO.getTemplateName());

        try {
            mailService.sendTemplate(userEmailVO.getEmail(), mailTemplate, params).get();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getErrorResult("发送失败");
        }

        //放在下面防止邮件发送失败
        //缓存邮箱验证码  邮箱key，code
        redisService.cacheEmailCode(userEmailVO.getEmail(),code);
        return ResultGenerator.getSuccessResult("发送成功");
    }


    @PostMapping(value = "/uploadImage")
    @ApiOperation(value = "/uploadImage", notes = "上传图片")
    @NeedLogin
    public ResultBean uploadImage(@RequestParam("file") MultipartFile file){
        //验证是否是图片
        String contentType = file.getContentType();
        if (!contentType.contains("image")){
            return ResultGenerator.getErrorResult("请选择图片上传");
        }
        //验证图片大小  5M
        if (file.getSize() > 5 * 1024 * 1024){
            return ResultGenerator.getErrorResult("选择的图片太大了");
        }

        AbstractOss oss = new FileOss(request);
        String fileUrl = oss.uploadFile(file);
        if (fileUrl == null){
            return ResultGenerator.getErrorResult("文件上传失败");
        }
        return ResultGenerator.getSuccessResult(fileUrl,"上传成功");
    }
}
