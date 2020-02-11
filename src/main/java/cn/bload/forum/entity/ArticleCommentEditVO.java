package cn.bload.forum.entity;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/11 12:14
 * @describe 类描述:
 */
@Data
public class ArticleCommentEditVO {

    @ApiModelProperty(value = "评论内容")
    @NotBlank(message = "评论内容不能为空")
    private String articleComment;
}
