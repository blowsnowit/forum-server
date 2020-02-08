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

    TopicDTO getTopicInfo(String topicName);

    List<TopicDTO> getHotTopics(TopicQuery topicQuery);

}
