package cn.bload.forum.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.bload.forum.model.ArticleTag;

/**
 * <p>
 * 文章标签
 Mapper 接口
 * </p>
 *
 * @author blowsnow
 * @since 2020-01-18
 */
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    String selectTagsByArticleId(Integer articleId);
}
