package cn.bload.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.apache.ibatis.transaction.TransactionException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.bload.forum.dao.ArticleMapper;
import cn.bload.forum.dao.ArticleTagMapper;
import cn.bload.forum.dao.ArticleTopicMapper;
import cn.bload.forum.dao.ArticleViewMapper;
import cn.bload.forum.dao.TagMapper;
import cn.bload.forum.dao.TopicMapper;
import cn.bload.forum.entity.dto.ArticleCommentDTO;
import cn.bload.forum.entity.dto.ArticleDTO;
import cn.bload.forum.entity.dto.ArticleUserDTO;
import cn.bload.forum.entity.query.ArticleQuery;
import cn.bload.forum.entity.vo.ArticleVO;
import cn.bload.forum.exception.MyRuntimeException;
import cn.bload.forum.model.Article;
import cn.bload.forum.model.ArticleTag;
import cn.bload.forum.model.ArticleTopic;
import cn.bload.forum.model.ArticleView;
import cn.bload.forum.model.Tag;
import cn.bload.forum.model.Topic;
import cn.bload.forum.service.ArticleCommentService;
import cn.bload.forum.service.ArticleService;
import cn.bload.forum.service.RedisService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author blowsnow
 * @since 2020-01-18
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Resource
    ArticleMapper articleMapper;
    @Resource
    ArticleTopicMapper articleTopicMapper;
    @Resource
    ArticleTagMapper articleTagMapper;
    @Resource
    TopicMapper topicMapper;
    @Resource
    TagMapper tagMapper;
    @Resource
    ArticleViewMapper articleViewMapper;
    @Resource
    ArticleCommentService articleCommentService;
    @Resource
    RedisService redisService;

    @Override
    public List<ArticleDTO> getArticles(ArticleQuery articleQuery) {
        articleQuery.setOp(false);
        List<ArticleDTO> articles = articleMapper.getArticles(articleQuery);

        //设置用户在线状态
        for (ArticleDTO article : articles) {
            ArticleUserDTO userDTO = article.getUserDTO();
            userDTO.setIsOnline(redisService.getUserOnline(userDTO.getUserId()));
        }
        return articles;
    }

    @Override
    public List<ArticleDTO> getOpArticles(ArticleQuery articleQuery) {
        articleQuery.setOp(true);
        return articleMapper.getArticles(articleQuery);
    }

    @Override
    public ArticleDTO getArticle(Integer articleId) {
        ArticleDTO article = articleMapper.getArticle(articleId);

        List<ArticleCommentDTO> articleComments = articleCommentService.getArticleCommentsAuto(articleId);
        article.setArticleComments(articleComments);

        //设置用户在线状态
        ArticleUserDTO userDTO = article.getUserDTO();
        userDTO.setIsOnline(redisService.getUserOnline(userDTO.getUserId()));

        for (ArticleCommentDTO articleComment : articleComments) {
            articleComment.getUserDTO().setIsOnline(redisService.getUserOnline(articleComment.getUserDTO().getUserId()));
        }

        return article;
    }

    @Override
    public void addArticle(Article article) {
        int result = articleMapper.insert(article);
        if (result == 0){
            throw new MyRuntimeException("添加失败");
        }
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addArticle(ArticleVO articleVO, Integer userId) {
        Article article = articleVO.toArticle();
        article.setUserId(userId);
        article.setArticleAddTime(new Date());
        article.setArticleUpdateTime(new Date());
        addArticle(article);

        doArticleTopicAndTag(article.getArticleId(),articleVO);

        return article.getArticleId();
    }

    @Override
    public void saveArticle(Integer articleId,ArticleVO articleVO) {
        Article article = articleVO.toArticle();
        article.setArticleId(articleId);
        article.setArticleUpdateTime(new Date());
        int result = articleMapper.updateById(article);
        if (result != 1){
            throw new MyRuntimeException("更新失败");
        }

        doArticleTopicAndTag(articleId,articleVO);
    }

    @Override
    public void saveArticleBeforeCheck(Integer articleId, Integer userId, ArticleVO articleVO) {
        ArticleDTO articleDTO = this.getArticle(articleId);
        if (articleDTO == null){
            throw new MyRuntimeException("当前文章不存在");
        }
        //验证文章归属的用户
        if (!articleDTO.getUserDTO().getUserId().equals(userId)){
            throw new MyRuntimeException("当前文章不属于你的，不允许编辑");
        }

        saveArticle(articleId,articleVO);
    }

    @Override
    public void saveArticleStatus(Integer articleId, Integer articleStatus) {
        Article article = new Article();
        article.setArticleId(articleId);
        article.setArticleStatus(articleStatus);
        articleMapper.updateById(article);
    }

    @Override
    public void saveArticleStatusBeforeCheck(Integer articleId, Integer userId, Integer articleStatus) {
        ArticleDTO articleDTO = this.getArticle(articleId);
        if (articleDTO == null){
            throw new MyRuntimeException("当前文章不存在");
        }
        //验证文章归属的用户
        if (!articleDTO.getUserDTO().getUserId().equals(userId)){
            throw new MyRuntimeException("当前文章不属于你的，不允许编辑");
        }
        saveArticleStatus(articleId,articleStatus);
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addArticleView(Integer articleId) {
        ArticleView articleView = new ArticleView();
        articleView.setArticleId(articleId);
        articleView.setAddTime(new Date());
        int result = articleViewMapper.insert(articleView);
        if (result != 1){
            throw new TransactionException("添加阅览记录失败");
        }

        articleMapper.updateArticleView(articleId);
    }

    @Override
    public void topArticle(Integer articleId, Integer articleTop) {
        Article article = new Article();
        article.setArticleId(articleId);
        article.setArticleTop(articleTop);
        articleMapper.updateById(article);
    }

    //处理标签和话题
    void doArticleTopicAndTag(Integer articleId,ArticleVO articleVO){
        //删除原来的标签和话题
        QueryWrapper<ArticleTopic> articleTopicQueryWrapper = new QueryWrapper<>();
        articleTopicQueryWrapper.lambda().eq(ArticleTopic::getArticleId,articleId);
        articleTopicMapper.delete(articleTopicQueryWrapper);

        QueryWrapper<ArticleTag> articleTagQueryWrapper = new QueryWrapper<>();
        articleTagQueryWrapper.lambda().eq(ArticleTag::getArticleId,articleId);
        articleTagMapper.delete(articleTagQueryWrapper);


        List<String> articleTopics = articleVO.getArticleTopics();
        if (articleTopics != null){
            for (String topicName : articleTopics) {
                //获取这个话题的id
                QueryWrapper<Topic> topicQueryWrapper = new QueryWrapper();
                topicQueryWrapper.lambda().eq(Topic::getTopicName,topicName);
                Topic topic = topicMapper.selectOne(topicQueryWrapper);
                if (topic == null){
                    topic = new Topic();
                    topic.setTopicName(topicName);
                    topic.setTopicAddTime(new Date());
                    topicMapper.insert(topic);
                }
                ArticleTopic articleTopic = new ArticleTopic();
                articleTopic.setArticleId(articleId);
                articleTopic.setTopicId(topic.getTopicId());
                articleTopic.setAddTime(new Date());
                int result = articleTopicMapper.insert(articleTopic);
                if (result != 1){
                    throw new MyRuntimeException("插入话题失败");
                }
            }
        }


        List<String> articleTags = articleVO.getArticleTags();
        if (articleTags != null){
            for (String tagName : articleTags) {
                //获取这个话题的id
                QueryWrapper<Tag> tagQueryWrapper = new QueryWrapper();
                tagQueryWrapper.lambda().eq(Tag::getTagName,tagName);
                Tag tag = tagMapper.selectOne(tagQueryWrapper);
                if (tag == null){
                    tag = new Tag();
                    tag.setTagName(tagName);
                    tag.setTagAddTime(new Date());
                    tagMapper.insert(tag);
                }
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(articleId);
                articleTag.setTagId(tag.getTagId());
                int result = articleTagMapper.insert(articleTag);
                if (result != 1){
                    throw new MyRuntimeException("插入标签失败");
                }
            }
        }
    }
}
