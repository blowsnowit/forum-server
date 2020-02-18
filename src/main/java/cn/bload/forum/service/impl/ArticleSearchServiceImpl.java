package cn.bload.forum.service.impl;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import cn.bload.forum.dao.ArticleMapper;
import cn.bload.forum.dao.ArticleSearchRepository;
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
@Service
public class ArticleSearchServiceImpl implements ArticleSearchService {
    @Autowired
    ArticleSearchRepository articleSearchRepository;
    @Autowired
    ArticleMapper articleMapper;

    @Override
    public void addArticleSearch(Integer articleId) {
        Article article = articleMapper.selectById(articleId);
        addArticleSearch(article);
    }

    @Override
    public void addArticleSearch(Article article) {
        ArticleSearchDTO articleSearchDTO = new ArticleSearchDTO(article);
        articleSearchRepository.save(articleSearchDTO);
    }

    @Override
    public void delArticleSearch(Integer articleId) {
        articleSearchRepository.deleteById(articleId);
    }

    @Override
    public Page<ArticleSearchDTO> searchArticles(ArticleQuery articleQuery) {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(articleQuery.getSearch(), "articleTitle", "articleContent");
        queryBuilder.withQuery(multiMatchQueryBuilder);
        queryBuilder.withSort(SortBuilders.fieldSort("articleId").order(SortOrder.DESC));

        // 分页
        int page = Integer.parseInt("" + articleQuery.getPage().getCurrent()) - 1;
        int size = Integer.parseInt("" + articleQuery.getPage().getSize());
        queryBuilder.withPageable(PageRequest.of(page,size));
        Page<ArticleSearchDTO> articleSearchDTOPage = articleSearchRepository.search(queryBuilder.build());

        return articleSearchDTOPage;
    }
}
