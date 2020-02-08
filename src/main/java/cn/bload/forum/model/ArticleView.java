package cn.bload.forum.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author blowsnow
 * @since 2020-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("forum_article_view")
@ApiModel(value="ArticleView对象", description="")
public class ArticleView implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "article_view_id", type = IdType.AUTO)
    private Integer articleViewId;

    private Integer articleId;

    @ApiModelProperty(value = "添加时间")
    private Date addTime;


}
