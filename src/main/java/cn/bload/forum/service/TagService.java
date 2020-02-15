package cn.bload.forum.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

import cn.bload.forum.entity.dto.TagDTO;
import cn.bload.forum.entity.query.TagQuery;
import cn.bload.forum.model.Tag;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author blowsnow
 * @since 2020-01-20
 */
public interface TagService extends IService<Tag> {

    List<TagDTO> getHotTags(TagQuery tagQuery);

}
