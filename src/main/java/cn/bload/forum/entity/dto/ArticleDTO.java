package cn.bload.forum.entity.dto;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/1/20 8:54
 * @describe 类描述:
 */
@Data
@ApiModel(value = "文章对象")
public class ArticleDTO {

    @ApiModelProperty(value = "文章id")
    private Integer articleId;

    @ApiModelProperty(value = "文章标题")
    private String articleTitle;

    @ApiModelProperty(value = "文章内容")
    private String articleContent;

    @ApiModelProperty(value = "文章发布时间")
    private Date articleAddTime;

    @ApiModelProperty(value = "文章更新时间")
    private Date articleUpdateTime;

    @ApiModelProperty(value = "文章状态(1正常 0删除)")
    private Integer articleStatus;

    @ApiModelProperty(value = "文章阅览次数")
    private Integer articleView;

    @ApiModelProperty(value = "文章评论数量")
    private Integer articleCommentCount;

    @ApiModelProperty(value = "文章作者")
    private ArticleUserDTO userDTO;

    @ApiModelProperty(value = "文章话题列表")
    private List<String> articleTopics;

    @ApiModelProperty(value = "文章tag标签列表")
    private List<String> articleTags;


    @ApiModelProperty(value = "文章评论列表")
    private List<ArticleCommentDTO> articleComments;
}
