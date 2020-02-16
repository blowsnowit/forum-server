package cn.bload.forum.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/16 10:55
 * @describe 类描述:
 */
@Data
@ApiModel("用户通知配置对象")
public class UserNotifyConfigUpdateVO {

    @ApiModelProperty(value = "通知名称")
    private String userNotifyConfigName;

    @ApiModelProperty(value = "是否通知 1通知 0不通知")
    private Integer userNotifyConfigNotify;

    @ApiModelProperty(value = "是否邮件通知 1通知 0不通知")
    private Integer userNotifyConfigEmail;
}
