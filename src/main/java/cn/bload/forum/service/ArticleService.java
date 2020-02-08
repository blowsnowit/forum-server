package cn.bload.forum.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

import cn.bload.forum.entity.dto.ArticleDTO;
import cn.bload.forum.entity.query.ArticleQuery;
import cn.bload.forum.entity.vo.ArticleVO;
import cn.bload.forum.model.Article;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author blowsnow
 * @since 2020-01-18
 */
public interface ArticleService extends IService<Article> {

    /**
     * 获取全部文章
     * 自己的未删除也会获取，其他用户删除的则不显示
     * @param articleQuery
     * @return
     */
    List<ArticleDTO> getArticles(ArticleQuery articleQuery);

    /**
     * 获取指定的文章
     * @param articleId 文章id
     * @return 文章对象
     */
    ArticleDTO getArticle(Integer articleId);


    void addArticle(Article article);

    void saveArticle(Article article);

    Integer addArticle(ArticleVO articleVO, Integer userId);

    void saveArticle(Integer articleId, Integer userId, ArticleVO articleVO);

    void saveArticleStatus(Integer articleId, Integer userId, Integer articleStatus);

    /**
     * 添加文章阅览记录
     * @param articleId 文章id
     */
    void addArticleView(Integer articleId);

}
