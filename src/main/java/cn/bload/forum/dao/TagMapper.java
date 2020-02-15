package cn.bload.forum.dao;

import cn.bload.forum.entity.dto.TagDTO;
import cn.bload.forum.entity.query.TagQuery;
import cn.bload.forum.model.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author blowsnow
 * @since 2020-01-20
 */
public interface TagMapper extends BaseMapper<Tag> {

    List<TagDTO> getHotTags(TagQuery tagQuery);

}
