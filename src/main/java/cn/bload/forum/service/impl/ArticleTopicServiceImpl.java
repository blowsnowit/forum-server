package cn.bload.forum.service.impl;

import cn.bload.forum.model.ArticleTopic;
import cn.bload.forum.dao.ArticleTopicMapper;
import cn.bload.forum.service.ArticleTopicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * <p>
 * 文章话题 服务实现类
 * </p>
 *
 * @author blowsnow
 * @since 2020-01-18
 */
@Service("articleTopicService")
public class ArticleTopicServiceImpl extends ServiceImpl<ArticleTopicMapper, ArticleTopic> implements ArticleTopicService {
    @Resource
    ArticleTopicMapper articleTopicMapper;

}
