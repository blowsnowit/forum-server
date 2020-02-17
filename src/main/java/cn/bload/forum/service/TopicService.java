package cn.bload.forum.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

import cn.bload.forum.entity.dto.TopicDTO;
import cn.bload.forum.entity.query.TopicQuery;
import cn.bload.forum.model.Topic;

/**
 * <p>
 * 话题表 服务类
 * </p>
 *
 * @author blowsnow
 * @since 2020-01-20
 */
public interface TopicService extends IService<Topic> {

    /**
     * 获取话题信息
     * @param topicName 话题名称
     * @return
     */
    TopicDTO getTopicInfo(String topicName);

    /**
     * 获取热门话题
     * @param topicQuery
     * @return
     */
    List<TopicDTO> getHotTopics(TopicQuery topicQuery);

    /**
     * 修改话题描述
     * @param topicName 话题名称
     * @param topicDesc 话题描述
     */
    void saveTopicDesc(String topicName, String topicDesc);

}
