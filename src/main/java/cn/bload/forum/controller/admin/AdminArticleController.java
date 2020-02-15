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
import cn.bload.forum.entity.query.ArticleQuery;
import cn.bload.forum.entity.vo.ArticleVO;
import cn.bload.forum.service.ArticleService;
import cn.bload.forum.utils.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/15 13:14
 * @describe 类描述:
 */
@RestController
@RequestMapping("/api/admin/articles")
@Api(value = "/api/admin/articles",tags = "后台文章控制器")
public class AdminArticleController extends BaseController {
    @Autowired
    ArticleService articleService;

    @GetMapping("")
    @ApiOperation(value = "",notes = "获取全部文章列表")
    public ResultBean getArticles(ArticleQuery articleQuery){
        articleQuery.setPageOrders("a.article_top desc,a.article_id desc");
        Page page = articleQuery.createPage();
        page.setRecords(articleService.getOpArticles(articleQuery));
        return ResultGenerator.getSuccessResult(page);
    }

    @PutMapping("/{articleId}/{articleStatus}")
    @ApiOperation(value = "/{articleId}/{articleStatus}",notes = "修改文章状态")
    public ResultBean saveArticleStatus(@ApiParam(value = "文章id") @PathVariable Integer articleId,
                                        @ApiParam(value = "文章状态") @PathVariable Integer articleStatus){
        articleService.saveArticleStatus(articleId,articleStatus);
        return ResultGenerator.getSuccessResult("删除成功");
    }


    @PutMapping("/{articleId}")
    @ApiOperation(value = "/{articleId}",notes = "修改文章信息")
    public ResultBean saveArticle(@ApiParam(value = "文章id") @PathVariable Integer articleId,
                                  @RequestBody ArticleVO articleVO){
        articleService.saveArticle(articleId,articleVO);
        return ResultGenerator.getSuccessResult("删除成功");
    }

    //置顶文章
    @PutMapping("/top/{articleId}/{articleTop}")
    @ApiOperation(value = "/top/{articleId}/{articleTop}",notes = "修改文章信息")
    public ResultBean topArticle(@ApiParam(value = "文章id") @PathVariable Integer articleId,
                                 @ApiParam(value = "置顶序号") @PathVariable Integer articleTop){
        articleService.topArticle(articleId,articleTop);
        return ResultGenerator.getSuccessResult("");
    }

}
