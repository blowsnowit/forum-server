package cn.bload.forum.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import cn.bload.forum.base.BaseController;
import cn.bload.forum.base.ResultBean;
import cn.bload.forum.entity.vo.UserNotifyConfigUpdateVO;
import cn.bload.forum.model.UserNotifyConfig;
import cn.bload.forum.service.UserNotifyConfigService;
import cn.bload.forum.utils.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/12 18:25
 * @describe 类描述:
 */
@RestController
@RequestMapping("/api/notifyConfig")
@Api(value = "/api/notifyConfig",tags = "通知配置控制器")
public class NotifyConfigController extends BaseController {
    @Autowired
    UserNotifyConfigService notifyConfigService;


    @GetMapping("")
    @ApiOperation(value = "",notes = "获取我的通知配置列表")
    public ResultBean getNotifyConfigs(){
        List<UserNotifyConfig> notifyConfigs = notifyConfigService.getNotifyConfigs(getUserId());
        return ResultGenerator.getSuccessResult(notifyConfigs);
    }


    @PutMapping("")
    @ApiOperation(value = "",notes = "保存我的通知配置列表")
    public ResultBean saveNotifyConfigs(@RequestBody List<UserNotifyConfigUpdateVO> userNotifyConfigUpdateVOS){
        notifyConfigService.saveNotifyConfigs(getUserId(),userNotifyConfigUpdateVOS);
        return ResultGenerator.getSuccessResult();
    }

}
