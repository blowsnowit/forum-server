package cn.bload.forum.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.bload.forum.base.BaseController;
import cn.bload.forum.base.Page;
import cn.bload.forum.base.ResultBean;
import cn.bload.forum.entity.query.TagQuery;
import cn.bload.forum.service.TagService;
import cn.bload.forum.utils.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/15 14:36
 * @describe 类描述:
 */
@RestController
@RequestMapping("/api/admin/tag")
@Api(value = "/api/admin/tag",tags = "后台标签控制器")
public class AdminTagController extends BaseController {
    @Autowired
    TagService tagService;

    @GetMapping("")
    @ApiOperation(value = "",notes = "获取标签列表")
    public ResultBean getTags(TagQuery tagQuery){
        Page page = tagQuery.createPage();
        page.setRecords(tagService.getHotTags(tagQuery));
        return ResultGenerator.getSuccessResult(page);
    }
}
