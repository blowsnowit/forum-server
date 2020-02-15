package cn.bload.forum.entity.query;

import cn.bload.forum.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/15 15:27
 * @describe 类描述:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("标签搜索对象")
public class TagQuery extends BaseQuery {
}
