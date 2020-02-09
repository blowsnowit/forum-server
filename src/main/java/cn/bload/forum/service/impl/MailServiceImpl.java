package cn.bload.forum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

import cn.bload.forum.constenum.MailTemplate;
import cn.bload.forum.exception.MyRuntimeException;
import cn.bload.forum.model.Config;
import cn.bload.forum.service.ConfigService;
import cn.bload.forum.service.MailService;
import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/8 16:35
 * @describe 类描述:
 */
@Service
@Async("mailTaskExecutor")
public class MailServiceImpl implements MailService {
    @Autowired
    ConfigService configService;

    /**
     * 获取邮箱账号配置
     * TODO 尝试缓存等提高效率
     * @return
     */
    private MailAccount getMailAccount(){
        Cache<String,String> fifoCache = CacheUtil.newFIFOCache(3);
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


    @Override
    public Future<String> send(String to, String subject, String content, boolean isHtml){
        MailAccount account = getMailAccount();

        String send = MailUtil.send(account, to, subject, content, isHtml);
        return new AsyncResult<>(send);
    }


    @Override
    public Future<String> send(Collection<String> tos, String subject, String content, boolean isHtml){
        MailAccount account = getMailAccount();
        String send = MailUtil.send(account, tos, subject, content, isHtml);
        return new AsyncResult<>(send);
    }


    @Override
    public Future<String> sendTemplate(String to, MailTemplate mailTemplate, Map<String, Object> params) {
        if (to == null){
            return null;
        }
        if (mailTemplate == null){
            return null;
        }
        //内置变量
        params.put("email",to);
        params.put("date", DateUtil.format(new Date(), "yyyy-MM-dd"));
        params.put("time", DateUtil.format(new Date(), "HH:mm:ss"));
        params.put("datetime", DateUtil.now());


        Config config = configService.getConfig(mailTemplate.getTemplateName());
        if (config == null){
            throw new MyRuntimeException("无法发送模板邮件");
        }
        String template = config.getConfigValue();
        if (template == null){
            return null;
        }
        //替换模板中的变量
        Set<String> strings = params.keySet();
        for (String key : strings) {
            Object value = params.get(key);
            if (value != null){
                template = template.replaceAll("\\["+key+"\\]",value.toString());
            }
        }
        return send(to,mailTemplate.getSubject(),template,true);
    }

    @Override
    public Future<String> sendTemplate(String to, MailTemplate mailTemplate, Object object) {
        Map<String, Object> map = BeanUtil.beanToMap(object);
        return sendTemplate(to,mailTemplate,map);
    }
}
