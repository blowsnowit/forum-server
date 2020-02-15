package cn.bload.forum.entity.query;

import cn.bload.forum.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/15 14:45
 * @describe 类描述:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("用户搜索对象")
public class UserQuery extends BaseQuery {
    @ApiModelProperty(value = "搜索用户名")
    private String userName;

    @ApiModelProperty(value = "搜索用户昵称")
    private String userNick;

    @ApiModelProperty(value = "搜索用户邮箱")
    private String userEmail;
}
