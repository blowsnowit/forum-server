package cn.bload.forum.service.impl;

import cn.bload.forum.model.Tag;
import cn.bload.forum.dao.TagMapper;
import cn.bload.forum.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author blowsnow
 * @since 2020-01-20
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Resource
    TagMapper tagMapper;

}
