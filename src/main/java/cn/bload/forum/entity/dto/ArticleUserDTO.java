package cn.bload.forum.entity.dto;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/1/20 9:02
 * @describe 类描述:
 */
@Data
@ApiModel("文章用户对象")
public class ArticleUserDTO {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户账号")
    private String userName;

    @ApiModelProperty(value = "用户昵称")
    private String userNick;

    @ApiModelProperty(value = "用户签名/描述")
    private String userDesc;

    @ApiModelProperty(value = "用户注册时间")
    private Date userAddTime;

    @ApiModelProperty(value = "用户头像")
    private String userFace;

    @ApiModelProperty(value = "用户在线状态")
    private Boolean isOnline;
}
