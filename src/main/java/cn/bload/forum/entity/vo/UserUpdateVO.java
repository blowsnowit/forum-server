package cn.bload.forum.entity.vo;

import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;

import cn.bload.forum.model.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/8 10:53
 * @describe 类描述:
 */
@Data
public class UserUpdateVO {

    @NotBlank(message = "用户昵称不能为空")
    @ApiModelProperty(value = "用户昵称")
    private String userNick;

    @ApiModelProperty(value = "用户签名/描述")
    private String userDesc;

    @NotBlank(message = "用户头像不能为空")
    @ApiModelProperty(value = "用户头像")
    private String userFace;


    public User toUser(){
        User user = new User();
        BeanUtils.copyProperties(this,user);
        return user;
    }
}
