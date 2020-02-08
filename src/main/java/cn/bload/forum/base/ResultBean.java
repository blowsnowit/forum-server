package cn.bload.forum.base;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/1/13 16:11
 * @describe 类描述:
 */
@Data
@ApiModel(value = "结果集对象")
public class ResultBean<T> {

    private int code;

    private T data;

    private String message;
}
