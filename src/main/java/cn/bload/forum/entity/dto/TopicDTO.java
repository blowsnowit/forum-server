package cn.bload.forum.entity.dto;

import cn.bload.forum.model.Topic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/3 14:52
 * @describe 类描述:
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TopicDTO extends Topic {

    @ApiModelProperty("文章数量")
    private Integer articleNum;
}
