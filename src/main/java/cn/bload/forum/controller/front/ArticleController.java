package cn.bload.forum.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import cn.bload.forum.base.BaseController;
import cn.bload.forum.base.Page;
import cn.bload.forum.base.ResultBean;
import cn.bload.forum.entity.dto.ArticleDTO;
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
 * @date 创建时间 : 2020/1/20 8:51
 * @describe 类描述:
 */
@RestController
@RequestMapping("/api/articles")
@Api(value = "/api/articles",tags = "文章控制器")
public class ArticleController extends BaseController {
    @Autowired
    ArticleService articleService;

    @GetMapping("")
    @ApiOperation(value = "",notes = "获取全部文章列表")
    public ResultBean getArticles(ArticleQuery articleQuery){
        articleQuery.setNowUserId(getUserId());

        articleQuery.setPageOrders("a.article_id desc");
        Page page = articleQuery.createPage();
        page.setRecords(articleService.getArticles(articleQuery));
        return ResultGenerator.getSuccessResult(page);
    }

    @GetMapping("/hot")
    @ApiOperation(value = "",notes = "获取热门文章列表")
    public ResultBean getHotArticles(ArticleQuery articleQuery){
        articleQuery.setNowUserId(getUserId());
        articleQuery.setPageOrders("a.article_view desc");
        Page page = articleQuery.createPage();

        page.setRecords(articleService.getArticles(articleQuery));
        return ResultGenerator.getSuccessResult(page);
    }


    @GetMapping("/{articleId}")
    @ApiOperation(value = "",notes = "/获取指定文章")
    public ResultBean getArticle(@ApiParam(value = "文章id") @PathVariable Integer articleId){
        ArticleDTO article = articleService.getArticle(articleId);
        //已被删除 且 不属于当前用户的文章
        if (article.getArticleStatus() != 1 && !article.getUserDTO().getUserId().equals(getUserId())){
            return ResultGenerator.getErrorResult("当前文章已被删除");
        }
        return ResultGenerator.getSuccessResult(article);
    }

    @PostMapping("")
    @ApiOperation(value = "",notes = "发布文章")
    public ResultBean addArticle(@Valid @RequestBody ArticleVO articleVO){
        Integer articleId = articleService.addArticle(articleVO, getUserIdCheck());
        return ResultGenerator.getSuccessResult(articleId,"添加成功");
    }


    @PutMapping("/{articleId}")
    @ApiOperation(value = "",notes = "编辑文章")
    public ResultBean saveArticle(@ApiParam(value = "文章id") @PathVariable Integer articleId,
                                  @RequestBody ArticleVO articleVO){
        articleService.saveArticle(articleId,getUserIdCheck(),articleVO);
        return ResultGenerator.getSuccessResult("编辑成功");
    }

    @PutMapping("/{articleId}/{articleStatus}")
    @ApiOperation(value = "",notes = "修改文章状态")
    public ResultBean saveArticleStatus(@ApiParam(value = "文章id") @PathVariable Integer articleId,
                                        @ApiParam(value = "文章状态") @PathVariable Integer articleStatus){
        articleService.saveArticleStatus(articleId,getUserIdCheck(),articleStatus);
        return ResultGenerator.getSuccessResult("删除成功");
    }

    @PostMapping("/view/{articleId}")
    @ApiOperation(value = "",notes = "添加文章阅览记录")
    public ResultBean addArticleView(@ApiParam(value = "文章id") @PathVariable Integer articleId){
        if (articleId == null){
            ResultGenerator.getErrorResult("文章不存在");
        }
        //TODO 后续考虑 记录IP，防止用户恶意调用
        articleService.addArticleView(articleId);
        return ResultGenerator.getSuccessResult(articleId,"添加成功");
    }
}
