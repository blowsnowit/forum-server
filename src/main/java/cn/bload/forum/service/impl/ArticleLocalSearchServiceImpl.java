package cn.bload.forum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import cn.bload.forum.base.Page;
import cn.bload.forum.dao.ArticleMapper;
import cn.bload.forum.entity.dto.ArticleDTO;
import cn.bload.forum.entity.dto.ArticleSearchDTO;
import cn.bload.forum.entity.query.ArticleQuery;
import cn.bload.forum.model.Article;
import cn.bload.forum.service.ArticleSearchService;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/18 14:01
 * @describe 类描述:
 */
@ConditionalOnProperty(value = "elsearch", havingValue = "false", matchIfMissing = false)
@Service("articleSearchService")
public class ArticleLocalSearchServiceImpl implements ArticleSearchService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public void addArticleSearch(Integer articleId) {
//        Article article = articleMapper.selectById(articleId);
//        addArticleSearch(article);
    }

    @Override
    public void addArticleSearch(Article article) {
//        ArticleSearchDTO articleSearchDTO = new ArticleSearchDTO(article);
//        articleSearchRepository.save(articleSearchDTO);
    }

    @Override
    public void delArticleSearch(Integer articleId) {
//        articleSearchRepository.deleteById(articleId);
    }

    @Override
    public Page<ArticleSearchDTO> searchArticles(ArticleQuery articleQuery) {
        List<ArticleDTO> articles = articleMapper.getArticles(articleQuery);
        List<ArticleSearchDTO> articleSearchDTOS = new ArrayList<>();
        for (ArticleDTO article : articles) {
            articleSearchDTOS.add(article.toArticleSearchDTO());
        }
        Page<ArticleSearchDTO> page = new Page<>();
        page.setTotal(articleQuery.getPage().getTotal());
        page.setRecords(articleSearchDTOS);
        return page;
    }
}
