package cn.bload.forum.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2020-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("forum_user_notify_config")
@ApiModel(value="UserNotifyConfig对象", description="")
public class UserNotifyConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_notify_config_id", type = IdType.AUTO)
    private Integer userNotifyConfigId;

    @ApiModelProperty(value = "0表示默认全局的")
    private Integer userId;

    @ApiModelProperty(value = "通知名称")
    private String userNotifyConfigName;

    @ApiModelProperty(value = "通知key值")
    private String userNotifyConfigValue;

    @ApiModelProperty(value = "是否通知 1通知 0不通知")
    private Integer userNotifyConfigNotify;

    @ApiModelProperty(value = "是否邮件通知 1通知 0不通知")
    private Integer userNotifyConfigEmail;


}
