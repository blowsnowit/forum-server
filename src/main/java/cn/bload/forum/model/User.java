package cn.bload.forum.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

import cn.bload.forum.entity.dto.ArticleUserDTO;
import cn.bload.forum.entity.dto.UserDTO;
import cn.bload.forum.utils.TokenUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author blowsnow
 * @since 2020-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("forum_user")
@ApiModel(value="User对象", description="用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty(value = "用户账号")
    private String userName;

    @ApiModelProperty(value = "用户密码(md5加密)")
    private String userPassword;

    @ApiModelProperty(value = "用户邮箱")
    private String userEmail;

    @ApiModelProperty(value = "用户昵称")
    private String userNick;

    @ApiModelProperty(value = "用户签名/描述")
    private String userDesc;

    @ApiModelProperty(value = "用户头像")
    private String userFace;

    @ApiModelProperty(value = "用户状态 1正常 0禁言 -1删除了")
    private Integer userStatus;

    @ApiModelProperty(value = "用户注册时间")
    private Date userAddTime;

    @ApiModelProperty(value = "是否是管理员 1是 0不是")
    private Integer userOp;

    //转换为user视图对象
    public UserDTO toUserDTO(){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(this,userDTO);
        //给用户token
        String token = TokenUtil.getToken(this);
        userDTO.setToken(token);
        return userDTO;
    }

    public ArticleUserDTO toArticleUserDTO(){
        ArticleUserDTO userDTO = new ArticleUserDTO();
        BeanUtils.copyProperties(this,userDTO);
        return userDTO;
    }
}
