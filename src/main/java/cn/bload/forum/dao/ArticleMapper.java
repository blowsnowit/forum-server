package cn.bload.forum.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import cn.bload.forum.entity.dto.ArticleDTO;
import cn.bload.forum.entity.query.ArticleQuery;
import cn.bload.forum.model.Article;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author blowsnow
 * @since 2020-01-18
 */
public interface ArticleMapper extends BaseMapper<Article> {

    List<ArticleDTO> getArticlesByIds(@Param("articleIds") List<Integer> articleIds);

    List<ArticleDTO> getArticles(ArticleQuery articleQuery);

    ArticleDTO getArticle(Integer articleId);

    void updateArticleView(Integer articleId);



}
