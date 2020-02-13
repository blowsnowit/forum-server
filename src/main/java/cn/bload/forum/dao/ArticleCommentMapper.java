package cn.bload.forum.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import cn.bload.forum.entity.dto.ArticleCommentDTO;
import cn.bload.forum.model.ArticleComment;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author blowsnow
 * @since 2020-02-11
 */
public interface ArticleCommentMapper extends BaseMapper<ArticleComment> {

    List<ArticleCommentDTO> getArticleComments(Integer articleId);

    ArticleCommentDTO getArticleComment(Integer articleCommentId);
}
