package cn.bload.forum.entity.query;

import cn.bload.forum.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/1/20 9:05
 * @describe 类描述:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("文章搜索对象")
public class ArticleQuery extends BaseQuery {

    @ApiModelProperty(value = "搜索标签名")
    private String tagName;

    @ApiModelProperty(value = "搜索话题名")
    private String topicName;

    @ApiModelProperty(value = "指定用户")
    private Integer userId;

    @ApiModelProperty(value = "当前登录的用户id", hidden = true)
    private Integer nowUserId;

    @ApiModelProperty(value = "搜索文章标题")
    private String search;
}
