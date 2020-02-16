package cn.bload.forum.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.bload.forum.dao.ArticleCommentMapper;
import cn.bload.forum.dao.ArticleMapper;
import cn.bload.forum.entity.dto.ArticleCommentDTO;
import cn.bload.forum.entity.query.CommentQuery;
import cn.bload.forum.entity.vo.ArticleCommentAddVO;
import cn.bload.forum.exception.MyRuntimeException;
import cn.bload.forum.model.Article;
import cn.bload.forum.model.ArticleComment;
import cn.bload.forum.service.ArticleCommentService;
import cn.bload.forum.service.UserNotifyService;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author blowsnow
 * @since 2020-02-11
 */
@Service("articleCommentService")
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleComment> implements ArticleCommentService {
    @Resource
    ArticleCommentMapper articleCommentMapper;
    @Resource
    ArticleMapper articleMapper;
    @Resource
    UserNotifyService userNotifyService;

    /**
     * 无限级枚举子评论
     * @param articleCommentId 父评论id  传0是父级的
     * @param articleComments 所有评论列表
     * @return 带有子父级关系的评论列表
     */
    private List<ArticleCommentDTO> doArticleComments(Integer articleCommentId,List<ArticleCommentDTO> articleComments){
        List<ArticleCommentDTO> articleCommentDTOS = new ArrayList<>();
        for (ArticleCommentDTO articleComment : articleComments) {
            if (articleCommentId.equals(articleComment.getParentArticleCommentId())){
                articleComment.setSonArticleComments(doArticleComments(articleComment.getArticleCommentId(),articleComments));
                articleCommentDTOS.add(articleComment);
            }
        }
        return articleCommentDTOS;
    }

    @Override
    public List<ArticleCommentDTO> getArticleCommentsAuto(Integer articleId) {
        List<ArticleCommentDTO> articleComments = articleCommentMapper.getArticleComments(articleId);
        return doArticleComments(0,articleComments);
    }

    @Override
    public Integer addArticleCommentBeforeCheck(Integer userId, Integer articleId, ArticleCommentAddVO articleCommentAddVO) {
        //检查文章是否可以被评论
        Article article = articleMapper.selectById(articleId);
        if (article == null){
            throw new MyRuntimeException("评论的文章不存在");
        }
        //判断文章状态 1正常
        if (article.getArticleStatus() != 1){
            throw new MyRuntimeException("评论的文章不存在");
        }
        //TODO 加一个 用户控制是否可以评论的字段

        if (articleCommentAddVO.getParentArticleCommentId() == null || articleCommentAddVO.getParentArticleCommentId() == 0){
            articleCommentAddVO.setParentArticleCommentId(0);
        }else{
            //验证父评论是否存在
            ArticleComment articleComment = articleCommentMapper.selectById(articleCommentAddVO.getParentArticleCommentId());
            if (articleComment == null){
                throw new MyRuntimeException("回复的评论不存在");
            }
        }
        ArticleComment articleComment = new ArticleComment();
        articleComment.setParentArticleCommentId(articleCommentAddVO.getParentArticleCommentId());
        articleComment.setArticleComment(articleCommentAddVO.getArticleComment());
        articleComment.setUserId(userId);
        articleComment.setArticleId(articleId);
        articleComment.setArticleCommentTime(new Date());

        int result = articleCommentMapper.insert(articleComment);
        if (result != 1){
            throw new MyRuntimeException("评论失败");
        }

        return articleComment.getArticleCommentId();
    }

    @Override
    public void editArticleCommentBeforeCheck(Integer userId, Integer articleCommentId, String articleComment) {
        ArticleComment myArticleComment = articleCommentMapper.selectById(articleCommentId);
        if (myArticleComment == null){
            throw new MyRuntimeException("评论不存在");
        }
        if (!myArticleComment.getUserId().equals(userId)){
            throw new MyRuntimeException("不能修改此评论");
        }

        ArticleComment newArticleComment = new ArticleComment();
        newArticleComment.setArticleCommentId(articleCommentId);
        newArticleComment.setArticleComment(articleComment);
        articleCommentMapper.updateById(newArticleComment);

    }

    @Override
    public void delArticleCommentBeforeCheck(Integer userId, Integer articleCommentId) {
        ArticleComment articleComment = articleCommentMapper.selectById(articleCommentId);
        if (articleComment == null){
            throw new MyRuntimeException("评论不存在");
        }
        if (!articleComment.getUserId().equals(userId)){
            throw new MyRuntimeException("不能修改此评论");
        }
        articleCommentMapper.deleteById(articleCommentId);
    }

    @Override
    public List<ArticleCommentDTO> getComments(CommentQuery commentQuery) {
        return articleCommentMapper.getArticleCommentsOp(commentQuery);
    }
}
