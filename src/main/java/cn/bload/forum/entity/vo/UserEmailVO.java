package cn.bload.forum.entity.vo;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/9 12:05
 * @describe 类描述:
 */
@Data
public class UserEmailVO {
    @ApiModelProperty(value = "邮箱",required = true)
    @NotBlank(message = "邮箱不能为空")
    private String email;


    @ApiModelProperty(value = "模板名称",required = true)
    @NotBlank(message = "模板名称不能为空")
    private String templateName;


    @ApiModelProperty(value = "图形验证码",required = true)
    @NotBlank(message = "图形验证码不能为空")
    private String code;

    @ApiModelProperty(value = "图形验证码token",required = true)
    @NotBlank(message = "请重新获取图形验证码")
    private String token;
}
