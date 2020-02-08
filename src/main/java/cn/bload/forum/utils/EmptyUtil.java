package cn.bload.forum.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/1/19 16:52
 * @describe 类描述:
 */
public class EmptyUtil {

    public static <T> boolean isEmpty(T t,String... names) {
        for (String name : names) {
            Field field = null;
            try {
                field = t.getClass().getField(name);
                Object o = field.get(t.getClass());
                if (o == null){
                    return true;
                }
                if (o instanceof String && ((String) o).isEmpty()){
                    return true;
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 验证全部值是否为空
     * @param t
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(T t){
        Field[] fields = t.getClass().getFields();
        List<String> names = new ArrayList<>();
        for (Field field : fields) {
            names.add(field.getName());
        }
        String[] strings = names.toArray(new String[names.size()]);
        return isEmpty(t,strings);
    }
}
