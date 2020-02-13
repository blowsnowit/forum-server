package cn.bload.forum.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.bload.forum.base.BaseController;
import cn.bload.forum.base.Page;
import cn.bload.forum.base.ResultBean;
import cn.bload.forum.entity.dto.UserNotifyDTO;
import cn.bload.forum.entity.query.NotifyQuery;
import cn.bload.forum.service.UserNotifyService;
import cn.bload.forum.utils.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/12 18:25
 * @describe 类描述:
 */
@RestController
@RequestMapping("/api/notify")
@Api(value = "/api/notify",tags = "通知控制器")
public class NotifyController extends BaseController {
    @Autowired
    UserNotifyService notifyService;


    //获取我的通知列表
    @GetMapping("")
    @ApiOperation(value = "",notes = "获取我的通知列表")
    public ResultBean getNotifys(NotifyQuery notifyQuery){
        Page<UserNotifyDTO> page = notifyQuery.createPage();
        notifyQuery.setNowUserId(getUserId());
        page.setRecords(notifyService.getNotifys(notifyQuery));
        page.setOthers(notifyService.getUnReadNotifyNum(getUserId()));
        return ResultGenerator.getSuccessResult(page);
    }

    @PutMapping("/{userNotifyId}")
    @ApiOperation(value = "/{userNotifyId}",notes = "已读指定通知")
    public ResultBean readNotify(@ApiParam("通知id") @PathVariable Integer userNotifyId){
        notifyService.readNotify(getUserId(),userNotifyId);
        return ResultGenerator.getSuccessResult();
    }

    @PutMapping("/all")
    @ApiOperation(value = "/all",notes = "已读全部通知")
    public ResultBean readAllNotify(){
        notifyService.readAllNotify(getUserId());
        return ResultGenerator.getSuccessResult();
    }

}
