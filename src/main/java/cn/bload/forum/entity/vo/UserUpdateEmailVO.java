package cn.bload.forum.entity.vo;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/8 10:57
 * @describe 类描述:
 */
@Data
public class UserUpdateEmailVO {

    @NotBlank(message = "用户旧密码不能为空")
    @ApiModelProperty(value = "用户旧密码")
    private String oldUserPassword;

    @NotBlank(message = "用户新邮箱不能为空")
    @ApiModelProperty(value = "用户新邮箱")
    private String email;

    @NotBlank(message = "邮箱验证码不能为空")
    @ApiModelProperty(value = "邮箱验证码")
    private String emailCode;
}
