package cn.bload.forum.service.impl;

import cn.bload.forum.model.ArticleView;
import cn.bload.forum.dao.ArticleViewMapper;
import cn.bload.forum.service.ArticleViewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author blowsnow
 * @since 2020-02-06
 */
@Service("articleViewService")
public class ArticleViewServiceImpl extends ServiceImpl<ArticleViewMapper, ArticleView> implements ArticleViewService {
    @Resource
    ArticleViewMapper articleViewMapper;

}
