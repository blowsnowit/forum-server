package cn.bload.forum.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

import cn.bload.forum.entity.dto.ArticleCommentDTO;
import cn.bload.forum.entity.vo.ArticleCommentAddVO;
import cn.bload.forum.model.ArticleComment;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author blowsnow
 * @since 2020-02-11
 */
public interface ArticleCommentService extends IService<ArticleComment> {

    /**
     * 获取文章评论
     * 自动上下级
     * @param articleId 文章id
     * @return
     */
    List<ArticleCommentDTO> getArticleCommentsAuto(Integer articleId);

    /**
     * 添加评论并且检查
     * @param userId 用户id
     * @param articleId 文章id
     * @param articleCommentAddVO
     */
    Integer addArticleCommentBeforeCheck(Integer userId, Integer articleId, ArticleCommentAddVO articleCommentAddVO);

    /**
     * 修改评论并且检查
     * @param userId 用户id
     * @param articleCommentId 评论id
     * @param articleComment 文章内容
     */
    void editArticleCommentBeforeCheck(Integer userId, Integer articleCommentId, String articleComment);

    /**
     * 删除评论并且检查
     * @param userId 用户id
     * @param articleCommentId 评论id
     */
    void delArticleCommentBeforeCheck(Integer userId, Integer articleCommentId);

}
