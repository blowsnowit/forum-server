package cn.bload.forum.entity.query;

import cn.bload.forum.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/12 18:52
 * @describe 类描述:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("通知搜索对象")
public class NotifyQuery extends BaseQuery {
    @ApiModelProperty(value = "当前登录的用户id", hidden = true)
    private Integer nowUserId;

    @ApiModelProperty(value = "已读状态 null全部，1已读 0未读")
    private Integer readStatus;

}
