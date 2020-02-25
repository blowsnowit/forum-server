package cn.bload.forum.utils.oss;

import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import cn.bload.forum.utils.SpringUtil;


/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/12 13:59
 * @describe 类描述: 本地文件oss存储方式
 */

public class FileOss extends AbstractOss{
    private String url;

    public FileOss(String url) {
        this.url = url;
    }

    public FileOss(HttpServletRequest request) {
        String host = request.getServerName(); //获取域名
        Integer port = request.getServerPort(); //获取端口号
        if (port == 80){
            this.url = "http://" + host;
        }else{
            this.url = "http://" + host + ":" + port;
        }
    }

    @Override
    public String uploadFile(String fileName, MultipartFile file) {
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        Environment environment = applicationContext.getEnvironment();
        String uploadPath = environment.getProperty("web.upload-path");
        File dest = new File(uploadPath + "/" + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //返回完整的图片地址
        String path = environment.getProperty("web.path");
        //拼接域名 + 端口 + path + 文件名

        return url + "/" + path + "/" + fileName;
    }
}
