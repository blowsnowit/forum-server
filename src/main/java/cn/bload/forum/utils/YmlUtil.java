package cn.bload.forum.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/12 14:58
 * @describe 类描述:
 */
public class YmlUtil {
    private static String bootstrap_file = "bootstrap.yml";

    private static Map<String,String> result = new HashMap<>();


    private static String resourcesPath = System.getProperty("user.dir") + "\\src\\main\\resources\\";

    /**
     * 根据文件名获取yml的文件内容
     * @return
     */
    public static Map<String,Object> getYmlByFileName(String fileName){
        Yaml yaml = new Yaml();
        if (fileName != null){
            bootstrap_file = fileName;
        }
        Iterable<Object> objects = null;
        try {
            objects = yaml.loadAll(new FileInputStream(new File(resourcesPath + bootstrap_file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Map<String,Object> next = (Map<String,Object>)(objects.iterator().next());
        return next;
    }

    public static Object getYmlValue(String fileName,String keyStr){
        Map<String, Object> map = getYmlByFileName(fileName);
        String[] split = keyStr.split("\\.");
        int index = 0;

        Object value = null;
        for (String key:split){
            if (value!=null){
                value = ((Map<String, Object>)value).get(key);
            }else{
                value = map.get(key);
            }
        }
        return value;
    }

}
