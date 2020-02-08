package cn.bload.forum.service.impl;

import cn.bload.forum.model.ArticleTag;
import cn.bload.forum.dao.ArticleTagMapper;
import cn.bload.forum.service.ArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * <p>
 * 文章标签
 服务实现类
 * </p>
 *
 * @author blowsnow
 * @since 2020-01-18
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
    @Resource
    ArticleTagMapper articleTagMapper;

}
