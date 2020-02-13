package cn.bload.forum.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

import cn.bload.forum.entity.dto.ArticleCommentDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author blowsnow
 * @since 2020-02-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("forum_article_comment")
@ApiModel(value="ArticleComment对象", description="评论表")
public class ArticleComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "article_comment_id", type = IdType.AUTO)
    private Integer articleCommentId;

    @ApiModelProperty(value = "父文章评论id")
    private Integer parentArticleCommentId;

    @ApiModelProperty(value = "关联的文章id")
    private Integer articleId;

    @ApiModelProperty(value = "评论用户id")
    private Integer userId;

    @ApiModelProperty(value = "评论内容")
    private String articleComment;

    @ApiModelProperty(value = "评论时间")
    private Date articleCommentTime;

    public ArticleCommentDTO toArticleCommentDTO(){
        ArticleCommentDTO articleCommentDTO = new ArticleCommentDTO();
        BeanUtils.copyProperties(this,articleCommentDTO);
        return articleCommentDTO;
    }
}
