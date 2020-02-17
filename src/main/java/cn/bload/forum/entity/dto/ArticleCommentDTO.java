package cn.bload.forum.entity.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/11 11:20
 * @describe 类描述:
 */
@Data
public class ArticleCommentDTO implements Serializable {

    @ApiModelProperty(value = "文章评论id")
    private Integer articleCommentId;

    @ApiModelProperty(value = "父文章评论id")
    private Integer parentArticleCommentId;

    @ApiModelProperty(value = "关联的文章id")
    private Integer articleId;

    @ApiModelProperty(value = "评论内容")
    private String articleComment;

    @ApiModelProperty(value = "评论时间")
    private Date articleCommentTime;

    @ApiModelProperty(value = "评论状态 1正常 0删除")
    private Integer articleCommentStatus;

    @ApiModelProperty(value = "文章")
    private ArticleDTO articleDTO;

    @ApiModelProperty(value = "评论用户")
    private ArticleUserDTO userDTO;

    @ApiModelProperty(value = "子评论列表")
    private List<ArticleCommentDTO> sonArticleComments;
}
