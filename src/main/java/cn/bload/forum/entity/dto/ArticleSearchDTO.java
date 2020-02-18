package cn.bload.forum.entity.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import cn.bload.forum.model.Article;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/18 13:54
 * @describe 类描述:
 */
@Data
@Document(indexName = "article",type = "docs", shards = 1, replicas = 0)
public class ArticleSearchDTO {

    @Id
    private Integer articleId;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String articleTitle;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String articleContent;

    public ArticleSearchDTO() {
    }

    public ArticleSearchDTO(Article article) {
        BeanUtils.copyProperties(article,this);
    }
}
