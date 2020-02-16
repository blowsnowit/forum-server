package cn.bload.forum.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.bload.forum.base.BaseController;
import cn.bload.forum.base.ResultBean;
import cn.bload.forum.entity.ArticleCommentEditVO;
import cn.bload.forum.entity.vo.ArticleCommentAddVO;
import cn.bload.forum.service.ArticleCommentService;
import cn.bload.forum.service.UserNotifyService;
import cn.bload.forum.utils.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/11 12:10
 * @describe 类描述:
 */

@RestController
@RequestMapping("/api/comment")
@Api(value = "/api/comment",tags = "评论控制器")
public class ArticleCommentController extends BaseController {
    @Autowired
    ArticleCommentService articleCommentService;

    @PostMapping("/{articleId}")
    @ApiOperation(value = "/{articleId}",notes = "发表评论")
    public ResultBean addArticleComment(@ApiParam(value = "文章id") @PathVariable Integer articleId,
                                        @Validated @RequestBody ArticleCommentAddVO articleCommentAddVO){

        Integer commendId = articleCommentService.addArticleCommentBeforeCheck(getUserId(), articleId, articleCommentAddVO);

        return ResultGenerator.getSuccessResult("评论成功");
    }

    @PutMapping("/{articleCommentId}")
    @ApiOperation(value = "/{articleCommentId}",notes = "编辑文章评论")
    public ResultBean editArticleComment(@ApiParam(value = "文章评论id") @PathVariable Integer articleCommentId,
                                         @Validated @RequestBody ArticleCommentEditVO articleCommentEditVO){
        articleCommentService.editArticleCommentBeforeCheck(getUserId(),articleCommentId,articleCommentEditVO.getArticleComment());
        return ResultGenerator.getSuccessResult("修改成功");
    }


    @DeleteMapping("/{articleCommentId}")
    @ApiOperation(value = "/{articleCommentId}",notes = "删除评论")
    public ResultBean delArticleComment(@ApiParam(value = "文章评论id") @PathVariable Integer articleCommentId){

        articleCommentService.delArticleCommentBeforeCheck(getUserId(),articleCommentId);
        return ResultGenerator.getSuccessResult("删除成功");
    }

}
