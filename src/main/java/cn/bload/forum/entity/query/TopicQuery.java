package cn.bload.forum.entity.query;

import cn.bload.forum.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/6 16:50
 * @describe 类描述:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("话题搜索对象")
public class TopicQuery extends BaseQuery {

}
