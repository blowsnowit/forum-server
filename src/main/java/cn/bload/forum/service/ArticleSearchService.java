package cn.bload.forum.service;


import cn.bload.forum.base.Page;
import cn.bload.forum.entity.dto.ArticleSearchDTO;
import cn.bload.forum.entity.query.ArticleQuery;
import cn.bload.forum.model.Article;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/18 14:00
 * @describe 类描述:
 */
public interface ArticleSearchService{
    /**
     * 添加文章到搜索索引
     * @param articleId 文章id
     */
    void addArticleSearch(Integer articleId);

    void addArticleSearch(Article article);

    /**
     * 删除文章搜索索引
     * @param articleId 文章id
     */
    void delArticleSearch(Integer articleId);

    Page<ArticleSearchDTO> searchArticles(ArticleQuery articleQuery);

}
