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
@TableName("forum_user_notify_read")
@ApiModel(value="UserNotifyRead对象", description="")
public class UserNotifyRead implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_notify_read_id", type = IdType.AUTO)
    private Integer userNotifyReadId;

    @ApiModelProperty(value = "提醒id")
    private Integer userNotifyId;

    @ApiModelProperty(value = "阅读者id")
    private Integer userId;

    @ApiModelProperty(value = "阅读时间")
    private Date readTime;


}
