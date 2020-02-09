package cn.bload.forum.utils;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import cn.bload.forum.model.Config;
import cn.bload.forum.service.ConfigService;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/8 13:32
 * @describe 类描述:
 */
public class MyMailUtil {
    static ConfigService configService;

    @Autowired
    ConfigService configServiceMapping;

    @PostConstruct
    public void init() {
        configService = configServiceMapping;
    }

    /**
     * 获取邮箱账号配置
     * TODO 尝试缓存等提高效率
     * @return
     */
    private static MailAccount getMailAccount(){
        List<Config> configList = configService.getConfigsByType("邮箱");
        MailAccount account = new MailAccount();
        for (Config config : configList) {
            String value = config.getConfigValue();
            switch (config.getConfigKey()){
                case "mail_host":
                    account.setHost(value);
                    break;
                case "mail_port":
                    account.setPort(Integer.valueOf(value));
                    break;
                case "mail_ssl":
                    account.setSslEnable("true".equals(value));
                    break;
                case "mail_from":
                    account.setFrom(value);
                    break;
                case "mail_user":
                    account.setAuth(true);
                    account.setUser(value);
                    break;
                case "mail_pass":
                    account.setPass(value);
                    break;
                default:
                    break;
            }
        }
        return account;
    }

    public static String send(String to, String subject, String content, boolean isHtml){
        MailAccount account = getMailAccount();
        return MailUtil.send(account, to, subject, content, isHtml);
    }
    public static String send(Collection<String> tos, String subject, String content, boolean isHtml){
        MailAccount account = getMailAccount();
        return MailUtil.send(account, tos, subject, content, isHtml);
    }

    public static void main(String[] args){
        MailAccount account = new MailAccount();
        account.setHost("smtp.exmail.qq.com");
        account.setPort(465);
        account.setSslEnable(true);
        account.setAuth(true);
        account.setFrom("bl@bload.cn");
        account.setUser("bl@bload.cn");
        account.setPass("Asdw112233");


        MailUtil.send(account, "2426477553@qq.com", "测试", "<h1>hello world</h1>邮件来自Hutool测试", true);
    }
}
