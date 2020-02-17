package cn.bload.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import cn.bload.forum.dao.ArticleTagMapper;
import cn.bload.forum.dao.TagMapper;
import cn.bload.forum.entity.dto.TagDTO;
import cn.bload.forum.entity.query.TagQuery;
import cn.bload.forum.model.ArticleTag;
import cn.bload.forum.model.Tag;
import cn.bload.forum.service.TagService;

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
    @Resource
    ArticleTagMapper articleTagMapper;

    @Override
    public List<TagDTO> getHotTags(TagQuery tagQuery) {
        return tagMapper.getHotTags(tagQuery);
    }

    @Override
    public TagDTO getTagInfo(String tagName) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Tag::getTagName,tagName);
        Tag tag = tagMapper.selectOne(queryWrapper);

        if (tag != null){
            QueryWrapper<ArticleTag> articleTagQueryWrapper = new QueryWrapper<>();
            articleTagQueryWrapper.lambda().eq(ArticleTag::getTagId,tag.getTagId());
            Integer num = articleTagMapper.selectCount(articleTagQueryWrapper);

            TagDTO tagDTO = new TagDTO();
            BeanUtils.copyProperties(tag,tagDTO);
            tagDTO.setArticleNum(num);
            return tagDTO;
        }
        return null;
    }
}
