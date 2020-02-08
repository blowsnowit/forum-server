package cn.bload.forum.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import cn.bload.forum.entity.dto.TopicDTO;
import cn.bload.forum.entity.query.TopicQuery;
import cn.bload.forum.model.Topic;

/**
 * <p>
 * 话题表 Mapper 接口
 * </p>
 *
 * @author blowsnow
 * @since 2020-01-20
 */
public interface TopicMapper extends BaseMapper<Topic> {

    Integer replaceInto(String articleTopic);

    List<TopicDTO> getHotTopics(TopicQuery topicQuery);

}
