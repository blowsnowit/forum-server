package cn.bload.forum.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.bload.forum.constenum.MailTemplate;
import cn.bload.forum.constenum.NotifyTargetType;
import cn.bload.forum.dao.ArticleCommentMapper;
import cn.bload.forum.dao.ArticleMapper;
import cn.bload.forum.dao.UserNotifyMapper;
import cn.bload.forum.dao.UserNotifyReadMapper;
import cn.bload.forum.entity.dto.ArticleCommentDTO;
import cn.bload.forum.entity.dto.ArticleDTO;
import cn.bload.forum.entity.dto.UserDTO;
import cn.bload.forum.entity.dto.UserNotifyDTO;
import cn.bload.forum.entity.query.NotifyQuery;
import cn.bload.forum.model.Article;
import cn.bload.forum.model.ArticleComment;
import cn.bload.forum.model.User;
import cn.bload.forum.model.UserNotify;
import cn.bload.forum.model.UserNotifyConfig;
import cn.bload.forum.service.MailService;
import cn.bload.forum.service.UserNotifyConfigService;
import cn.bload.forum.service.UserNotifyService;
import cn.bload.forum.service.UserService;
import cn.bload.forum.utils.IpUtil;
import cn.hutool.core.date.DateUtil;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author blowsnow
 * @since 2020-02-12
 */
@Service("userNotifyService")
public class UserNotifyServiceImpl extends ServiceImpl<UserNotifyMapper, UserNotify> implements UserNotifyService {
    @Resource
    UserNotifyMapper userNotifyMapper;
    @Resource
    UserNotifyReadMapper userNotifyReadMapper;
    @Resource
    ArticleMapper articleMapper;
    @Resource
    ArticleCommentMapper articleCommentMapper;
    @Autowired
    HttpServletRequest request;
    @Autowired
    UserNotifyConfigService userNotifyConfigService;
    @Autowired
    MailService mailService;
    @Autowired
    UserService userService;

    @Override
    public List<UserNotifyDTO> getNotifys(NotifyQuery notifyQuery) {
        List<UserNotifyDTO> notifyDTOS = getNotifysUnDo(notifyQuery);
        //处理数据
        for (UserNotifyDTO notifyDTO : notifyDTOS) {
            Integer target = notifyDTO.getTarget();

            Object object = null;
            switch (notifyDTO.getTargetType()){
                case "article":
                    ArticleDTO article = articleMapper.getArticle(target);
                    if (article.getArticleStatus() != 1 && !article.getUserDTO().getUserId().equals(notifyQuery.getNowUserId())){
                        break;
                    }
                    object = article;
                    break;
                case "comment":
                    ArticleCommentDTO articleComment = articleCommentMapper.getArticleComment(target);
                    if (articleComment == null) break;
                    ArticleDTO article2 = articleMapper.getArticle(articleComment.getArticleId());
                    List<ArticleCommentDTO> articleCommentDTOS = new ArrayList<>();
                    articleCommentDTOS.add(articleComment);
                    article2.setArticleComments(articleCommentDTOS);
                    object = article2;
                    break;
                default:
                    break;
            }
            notifyDTO.setTargetData(object);
        }


        return notifyDTOS;
    }

    @Override
    public Integer getUnReadNotifyNum(Integer userId) {
        return userNotifyMapper.getUnReadNotifyNum(userId);
    }

    @Override
    public List<UserNotifyDTO> getNotifysUnDo(NotifyQuery notifyQuery) {
        return userNotifyMapper.getNotifys(notifyQuery);
    }

    @Override
    public void readNotify(Integer userId, Integer userNotifyId) {
        List<Integer> ids = new ArrayList<>();
        ids.add(userNotifyId);

        userNotifyReadMapper.readNotifyByIds(userId,ids);
    }

    @Override
    public void readAllNotify(Integer userId) {
        NotifyQuery notifyQuery = new NotifyQuery();
        notifyQuery.setNowUserId(userId);
        notifyQuery.setReadStatus(0);
        //不分页
        notifyQuery.getPage().setSize(-1L);

        //获取所有未读的
        List<UserNotifyDTO> notifys = getNotifys(notifyQuery);

        List<Integer> ids = new ArrayList<>();
        for (UserNotifyDTO notify : notifys) {
            ids.add(notify.getUserNotifyId());
        }
        if (ids.size() == 0){
            return;
        }
        userNotifyReadMapper.readNotifyByIds(userId,ids);
    }

    @Override
    public void pushCommentNotify(Integer commentId) {
        ArticleComment articleComment = articleCommentMapper.selectById(commentId);

        Integer userId;
        if (articleComment.getParentArticleCommentId() == null || articleComment.getParentArticleCommentId() == 0){
            Article article = articleMapper.selectById(articleComment.getArticleId());
            userId = article.getUserId();
        }else{
            ArticleComment parentArticleComment = articleCommentMapper.selectById(articleComment.getParentArticleCommentId());
            userId = parentArticleComment.getUserId();
        }


        pushNotify(NotifyTargetType.COMMENT,commentId,userId,"");
    }

    @Override
    public void pushUserLoginNotify(UserDTO userDTO) {
        String ipAddr = IpUtil.getIpAddr(request);
        String time = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        String content = "于 " + time + " 在[" + ipAddr + "] 登录";
        pushNotify(NotifyTargetType.SYSTEM,null,userDTO.getUserId(),content);
    }


    @Override
    public void pushNotify(NotifyTargetType targetType, Integer target, Integer userId, String content){
        pushNotify(targetType,target,userId,content,null);
    }

    @Override
    public void pushNotify(NotifyTargetType targetType, Integer target, Integer userId, String content, MailTemplate mailTemplate) {
        //需要判断用户是否设置不接收
        UserNotifyConfig userNotifyConfig = userNotifyConfigService.getNotifyConfig(userId,targetType.getName());

        //判断是否网站通知
        if (userNotifyConfig.getUserNotifyConfigNotify() == 1){
            UserNotify userNotify = new UserNotify();
            userNotify.setTargetType(targetType.getName());
            userNotify.setTarget(target);
            userNotify.setCreateTime(new Date());
            userNotify.setUserId(userId);
            userNotify.setContent(content);
            userNotifyMapper.insert(userNotify);

            //websocket推送给用户
        }
        //判断是否邮件通知
        if (userNotifyConfig.getUserNotifyConfigEmail() == 1){
            //获取用户邮箱
            User user = userService.getById(userId);
            String userEmail = user.getUserEmail();
            if(mailTemplate == null){
                mailService.send(userEmail,"站内提醒",content,true);
            }else{
                mailService.sendTemplate(userEmail,mailTemplate,null);
            }
        }

    }


}
