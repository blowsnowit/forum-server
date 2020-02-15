package cn.bload.forum.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.bload.forum.base.BaseController;
import cn.bload.forum.base.Page;
import cn.bload.forum.base.ResultBean;
import cn.bload.forum.entity.query.UserQuery;
import cn.bload.forum.model.User;
import cn.bload.forum.service.UserService;
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
@RequestMapping("/api/admin/user")
@Api(value = "/api/admin/user",tags = "后台用户控制器")
public class AdminUserController extends BaseController {
    @Autowired
    UserService userService;

    @GetMapping("")
    @ApiOperation(value = "",notes = "获取用户列表")
    public ResultBean getUserList(UserQuery userQuery){
        Page page = userQuery.createPage();
        page.setRecords(userService.getUserList(userQuery));
        return ResultGenerator.getSuccessResult(page);
    }



    @PutMapping("/{userId}/{userStatus}")
    @ApiOperation(value = "/{userId}/{userStatus}",notes = "修改用户状态")
    public ResultBean saveUserStatus(@ApiParam(value = "用户id") @PathVariable Integer userId,
                                        @ApiParam(value = "用户状态") @PathVariable Integer userStatus){
        userService.saveUserStatus(userId,userStatus);
        return ResultGenerator.getSuccessResult("修改成功");
    }

    @PutMapping("/{userId}")
    @ApiOperation(value = "/{userId}",notes = "更新用户信息")
    public ResultBean saveUserInfo(@ApiParam(value = "用户id") @PathVariable Integer userId,
                                   @RequestBody User user){
        user.setUserId(userId);
        userService.updateUserInfo(user);
        return ResultGenerator.getSuccessResult("修改成功");
    }
}
