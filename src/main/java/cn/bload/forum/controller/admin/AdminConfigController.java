package cn.bload.forum.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import cn.bload.forum.base.BaseController;
import cn.bload.forum.base.ResultBean;
import cn.bload.forum.model.Config;
import cn.bload.forum.service.ConfigService;
import cn.bload.forum.utils.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/15 14:36
 * @describe 类描述:
 */
@RestController
@RequestMapping("/api/admin/config")
@Api(value = "/api/admin/config",tags = "后台设置控制器")
public class AdminConfigController extends BaseController {
    @Autowired
    ConfigService configService;

    @GetMapping("")
    @ApiOperation(value = "",notes = "获取配置列表")
    public ResultBean getConfigs(){
        Map<String, List<Config>> configs = configService.getConfigs();
        return ResultGenerator.getSuccessResult(configs);
    }

    //保存配置列表
    @PutMapping("")
    @ApiOperation(value = "",notes = "保存配置列表")
    public ResultBean saveConfigs(@ApiParam("配置列表 key,value") @RequestBody List<Config> configs){
        configService.saveConfigs(configs);
        return ResultGenerator.getSuccessResult();
    }

}
