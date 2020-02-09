package cn.bload.forum.constenum;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/9 12:29
 * @describe 类描述:
 */
public enum  MailTemplate {
    /**
     * 注册模板
     */
    REGISTER("Email 地址验证","mail_template_register"),

    /**
     * 注册模板
     */
    LOGIN("登录提醒","mail_template_login");


    @Getter
    @Setter
    private String subject;

    @Getter
    @Setter
    private String templateName;

    MailTemplate(String subject, String templateName) {
        this.subject = subject;
        this.templateName = templateName;
    }

    /**
     * 通过模板名称获取
     * @param templateName 模板名称
     * @return
     */
    public static MailTemplate getByTemplateName(String templateName) {
        MailTemplate[] mailTemplates = MailTemplate.values();
        for (MailTemplate mailTemplate : mailTemplates) {
            if (mailTemplate.getTemplateName().equals(templateName)){
                return mailTemplate;
            }
        }
        return null;
    }
}
