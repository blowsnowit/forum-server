package cn.bload.forum.service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;

import cn.bload.forum.constenum.MailTemplate;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/8 16:35
 * @describe 类描述:
 */
public interface MailService {

    /**
     * 异步发送邮件
     * @param to 被发送者
     * @param subject 邮件标题
     * @param content 邮件内容
     * @param isHtml 是否html
     * @return
     */
    Future<String> send(String to, String subject, String content, boolean isHtml);

    Future<String> send(Collection<String> tos, String subject, String content, boolean isHtml);

    /**
     * 从配置模板中
     * @param to 被发送者
     * @param mailTemplate 邮件模板
     * @param params 变量
     * @return
     */
    Future<String> sendTemplate(String to, MailTemplate mailTemplate, Map<String,Object> params);

    Future<String> sendTemplate(String to, MailTemplate mailTemplate, Object params);
}
