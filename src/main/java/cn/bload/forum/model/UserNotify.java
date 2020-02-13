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
 * 
 * </p>
 *
 * @author blowsnow
 * @since 2020-02-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("forum_user_notify")
@ApiModel(value="UserNotify对象", description="")
public class UserNotify implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_notify_id", type = IdType.AUTO)
    private Integer userNotifyId;

    @ApiModelProperty(value = "目标id(文章id/评论id)")
    private Integer target;

    @ApiModelProperty(value = "目标类型(article/comment/notice)")
    private String targetType;

    @ApiModelProperty(value = "用户id(0全体用户，否则指定用户)")
    private Integer userId;

    @ApiModelProperty(value = "通知内容")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
