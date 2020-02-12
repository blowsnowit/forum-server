package cn.bload.forum.utils.oss;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/12 14:02
 * @describe 类描述: 腾讯云cos
 */
public class QcloudOss extends AbstractOss {

    @Override
    public String uploadFile(String fileName, MultipartFile file) {
        return null;
    }
}
