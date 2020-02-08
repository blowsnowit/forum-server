package cn.bload.forum.utils;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/8 13:32
 * @describe 类描述:
 */
public class MyMailUtil {



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
