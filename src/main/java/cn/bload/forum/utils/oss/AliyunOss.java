package cn.bload.forum.utils.oss;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/12 14:00
 * @describe 类描述: 阿里云oss
 */
public class AliyunOss extends AbstractOss {

    @Override
    public String uploadFile(String fileName, MultipartFile file) {
        return null;
    }
}
