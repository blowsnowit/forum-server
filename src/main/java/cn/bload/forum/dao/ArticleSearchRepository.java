package cn.bload.forum.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

import cn.bload.forum.entity.dto.ArticleSearchDTO;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/18 16:13
 * @describe 类描述:
 */
public interface  ArticleSearchRepository extends ElasticsearchRepository<ArticleSearchDTO, Integer> {

}
