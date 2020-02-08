package cn.bload.forum.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/6 14:03
 * @describe 类描述:
 */

@Target(ElementType.METHOD)
public @interface NeedLogin {
}
