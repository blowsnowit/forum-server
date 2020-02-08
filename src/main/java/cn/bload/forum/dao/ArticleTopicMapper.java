package cn.bload.forum.dao;

import cn.bload.forum.model.ArticleTopic;
import io.swagger.models.auth.In;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 文章话题 Mapper 接口
 * </p>
 *
 * @author blowsnow
 * @since 2020-01-18
 */
public interface ArticleTopicMapper extends BaseMapper<ArticleTopic> {

    void inserts(Integer articleId, List<String> articleTopics);

    String selectTopicByArticleId(Integer articleId);
}
