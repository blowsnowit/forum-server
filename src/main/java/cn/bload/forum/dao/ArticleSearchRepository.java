package cn.bload.forum.dao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import cn.bload.forum.entity.dto.ArticleSearchDTO;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/18 16:13
 * @describe 类描述:
 */
@ConditionalOnProperty(value = "elsearch",havingValue = "true",matchIfMissing = true)
public interface  ArticleSearchRepository extends ElasticsearchRepository<ArticleSearchDTO, Integer> {

}
