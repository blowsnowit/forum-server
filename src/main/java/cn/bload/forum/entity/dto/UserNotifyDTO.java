package cn.bload.forum.entity.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/12 18:32
 * @describe 类描述:
 */
@Data
public class UserNotifyDTO {

    @ApiModelProperty(value = "通知id")
    private Integer userNotifyId;

    @ApiModelProperty(value = "目标id(文章id/评论id)")
    private Integer target;

    @ApiModelProperty(value = "目标数据(文章数据/评论数据)")
    private Object targetData;

    @ApiModelProperty(value = "目标类型(article/comment/notice)")
    private String targetType;

    @ApiModelProperty(value = "用户id(0全体用户，否则指定用户)")
    private Integer userId;

    @ApiModelProperty(value = "通知内容")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "是否已读 1已读 0未读")
    private Integer isRead;

    @ApiModelProperty(value = "已阅时间")
    private Date readTime;

}
