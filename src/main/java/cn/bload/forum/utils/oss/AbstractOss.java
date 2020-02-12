package cn.bload.forum.utils.oss;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/12 13:59
 * @describe 类描述:
 */
public abstract class AbstractOss {

    public String uploadFile(MultipartFile file){
        if(file.isEmpty()){
            return null;
        }
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名

        fileName = UUID.randomUUID() + suffixName; // 新文件名
        return uploadFile(fileName,file);
    }

    public abstract String uploadFile(String fileName,MultipartFile file);
}
