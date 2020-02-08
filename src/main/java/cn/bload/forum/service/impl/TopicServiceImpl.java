package cn.bload.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import cn.bload.forum.dao.ArticleTopicMapper;
import cn.bload.forum.dao.TopicMapper;
import cn.bload.forum.entity.dto.TopicDTO;
import cn.bload.forum.entity.query.TopicQuery;
import cn.bload.forum.model.ArticleTopic;
import cn.bload.forum.model.Topic;
import cn.bload.forum.service.TopicService;

/**
 * <p>
 * 话题表 服务实现类
 * </p>
 *
 * @author blowsnow
 * @since 2020-01-20
 */
@Service("topicService")
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {
    @Resource
    TopicMapper topicMapper;
    @Resource
    ArticleTopicMapper articleTopicMapper;


    @Override
    public TopicDTO getTopicInfo(String topicName) {
        QueryWrapper<Topic> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Topic::getTopicName,topicName);
        Topic topic = topicMapper.selectOne(queryWrapper);
        if (topic != null){
            QueryWrapper<ArticleTopic> articleTopicQueryWrapper = new QueryWrapper<>();
            articleTopicQueryWrapper.lambda().eq(ArticleTopic::getTopicId,topic.getTopicId());
            Integer num = articleTopicMapper.selectCount(articleTopicQueryWrapper);
            TopicDTO topicDTO = new TopicDTO();
            BeanUtils.copyProperties(topic,topicDTO);
            topicDTO.setArticleNum(num);
            return topicDTO;
        }
        return null;
    }

    @Override
    public List<TopicDTO> getHotTopics(TopicQuery topicQuery) {
        return topicMapper.getHotTopics(topicQuery);
    }
}
