package cn.bload.forum.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

import cn.bload.forum.constenum.MailTemplate;
import cn.bload.forum.constenum.NotifyTargetType;
import cn.bload.forum.entity.dto.UserDTO;
import cn.bload.forum.entity.dto.UserNotifyDTO;
import cn.bload.forum.entity.query.NotifyQuery;
import cn.bload.forum.model.UserNotify;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author blowsnow
 * @since 2020-02-12
 */
public interface UserNotifyService extends IService<UserNotify> {

    /**
     * 获取指定用户所有通知
     * @param notifyQuery
     * @return
     */
    List<UserNotifyDTO> getNotifys(NotifyQuery notifyQuery);

    /**
     * 获取未读通知私聊
     * @param userId 用户id
     * @return
     */
    Integer getUnReadNotifyNum(Integer userId);


    List<UserNotifyDTO> getNotifysUnDo(NotifyQuery notifyQuery);

    /**
     * 读取指定用户的指定通知
     * @param userId 用户id
     * @param userNotifyId 通知id
     */
    void readNotify(Integer userId,Integer userNotifyId);

    /**
     * 读取指定用户所有通知
     * @param userId 用户id
     */
    void readAllNotify(Integer userId);

    /**
     * 推送评论给文章作者
     * @param commentId 评论id
     *
     */
    void pushCommentNotify(Integer commentId);


    /**
     * 推送用户登录通知
     * @param userDTO 用户数据
     */
    void pushUserLoginNotify(UserDTO userDTO);


    /**
     * 推送给用户通知
     * @param targetType 目标类型(article/comment/notice)
     * @param target 目标id(文章id/评论id)
     * @param userId 用户id
     * @param content 通知内容
     */
    void pushNotify(NotifyTargetType targetType, Integer target, Integer userId, String content);

    /**
     * 推送给用户通知
     * @param targetType 目标类型(article/comment/notice)
     * @param target 目标id(文章id/评论id)
     * @param userId 用户id
     * @param content 通知内容
     * @param mailTemplate 邮件模板
     */
    void pushNotify(NotifyTargetType targetType, Integer target, Integer userId, String content, MailTemplate mailTemplate);


}
