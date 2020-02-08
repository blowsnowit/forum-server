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
 * 文章话题
 * </p>
 *
 * @author blowsnow
 * @since 2020-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("forum_article_topic")
@ApiModel(value="ArticleTopic对象", description="文章话题")
public class ArticleTopic implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章话题id")
    @TableId(value = "article_topic_id", type = IdType.AUTO)
    private Integer articleTopicId;

    @ApiModelProperty(value = "文章id")
    private Integer articleId;

    @ApiModelProperty(value = "话题id")
    private Integer topicId;

    @ApiModelProperty(value = "添加时间")
    private Date addTime;

}
