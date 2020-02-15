package cn.bload.forum.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.bload.forum.base.BaseController;
import cn.bload.forum.base.Page;
import cn.bload.forum.base.ResultBean;
import cn.bload.forum.entity.query.CommentQuery;
import cn.bload.forum.service.ArticleCommentService;
import cn.bload.forum.utils.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/15 14:35
 * @describe 类描述:
 */
@RestController
@RequestMapping("/api/admin/comment")
@Api(value = "/api/admin/comment",tags = "后台评论控制器")
public class AdminCommentController extends BaseController {
    @Autowired
    ArticleCommentService articleCommentService;


    @GetMapping("")
    @ApiOperation(value = "",notes = "获取评论列表")
    public ResultBean getComments(CommentQuery commentQuery){
        Page page = commentQuery.createPage();
        page.setRecords(articleCommentService.getComments(commentQuery));
        return ResultGenerator.getSuccessResult(page);
    }



    @DeleteMapping("/{articleCommentId}")
    @ApiOperation(value = "/{articleCommentId}",notes = "删除评论")
    public ResultBean delComment(@ApiParam("文章评论id") @PathVariable Integer articleCommentId){
        articleCommentService.removeById(articleCommentId);
        return ResultGenerator.getSuccessResult();
    }

}
