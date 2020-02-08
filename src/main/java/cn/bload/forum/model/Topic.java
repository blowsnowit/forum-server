package cn.bload.forum.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 话题表
 * </p>
 *
 * @author blowsnow
 * @since 2020-01-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("forum_topic")
@ApiModel(value="Topic对象", description="话题表")
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "topic_id", type = IdType.AUTO)
    private Integer topicId;

    @ApiModelProperty(value = "话题名称")
    private String topicName;

    @ApiModelProperty(value = "话题描述")
    private String topicDesc;

    private Date topicAddTime;


}
