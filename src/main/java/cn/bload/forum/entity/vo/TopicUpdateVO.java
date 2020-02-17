package cn.bload.forum.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/17 18:35
 * @describe 类描述:
 */
@Data
@ApiModel(value = "话题更新视图对象")
public class TopicUpdateVO {

    @ApiModelProperty(value = "话题描述")
    private String topicDesc;
}
