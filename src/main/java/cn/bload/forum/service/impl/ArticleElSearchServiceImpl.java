package cn.bload.forum.service.impl;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

import cn.bload.forum.base.Page;
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
@ConditionalOnProperty(value = "elsearch",havingValue = "true",matchIfMissing = true)
@Service("articleSearchService")
public class ArticleElSearchServiceImpl implements ArticleSearchService {
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
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(articleQuery.getSearchWord(), "articleTitle", "articleContent");
        queryBuilder.withQuery(multiMatchQueryBuilder);
        queryBuilder.withSort(SortBuilders.fieldSort("articleId").order(SortOrder.DESC));

        // 分页
        Page page = articleQuery.getPage();

        int current = Integer.parseInt("" + page.getCurrent()) - 1;
        int size = Integer.parseInt("" + page.getSize());
        queryBuilder.withPageable(PageRequest.of(current,size));
        org.springframework.data.domain.Page<ArticleSearchDTO> articleSearchDTOPage = articleSearchRepository.search(queryBuilder.build());

        List<ArticleSearchDTO> content = articleSearchDTOPage.getContent();

        page.setRecords(content);
        page.setTotal(articleSearchDTOPage.getTotalElements());
        return page;
    }
}
