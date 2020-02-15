package cn.bload.forum.entity.vo;

import org.springframework.beans.BeanUtils;

import java.util.List;

import javax.validation.constraints.NotBlank;

import cn.bload.forum.model.Article;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/1/20 13:52
 * @describe 类描述:
 */
@Data
@ApiModel(value = "文章视图对象")
public class ArticleVO {
    @ApiModelProperty(value = "文章标题")
    @NotBlank(message = "文章标题不能为空")
    private String articleTitle;

    @ApiModelProperty(value = "文章内容")
    @NotBlank(message = "文章内容不能为空")
    private String articleContent;

    @ApiModelProperty(value = "文章tag标签列表")
    private List<String> articleTags;

    @ApiModelProperty(value = "文章话题列表")
    private List<String> articleTopics;

    @ApiModelProperty(value = "文章状态(1正常 0删除)")
    private Integer articleStatus;

    @ApiModelProperty(value = "置顶文章 0取消置顶(管理员使用)",hidden = true)
    private Integer articleTop;

    public Article toArticle(){
        Article article = new Article();
        BeanUtils.copyProperties(this,article);
        return article;
    }
}
