package cn.bload.forum.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

import cn.bload.forum.entity.vo.UserNotifyConfigUpdateVO;
import cn.bload.forum.model.UserNotifyConfig;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author blowsnow
 * @since 2020-02-16
 */
public interface UserNotifyConfigService extends IService<UserNotifyConfig> {

    /**
     * 获取指定用户的指定通知配置
     * 会自动获取通用的
     * @param userId 用户id
     * @param value 配置value值
     * @return
     */
    UserNotifyConfig getNotifyConfig(Integer userId, String value);

    /**
     * 获取指定自己的指定通知配置
     * 不获取通用的
     * @param userId 用户id
     * @param name 配置名
     * @return
     */
    UserNotifyConfig getRealNotifyConfig(Integer userId, String name);

    /**
     * 获取指定用户全部通知配置
     * 会自动获取通用的
     * @param userId 用户id
     * @return
     */
    List<UserNotifyConfig> getNotifyConfigs(Integer userId);

    /**
     * 保存指定用户通知配置
     * @param userId 用户id
     * @param userNotifyConfigUpdateVOS 配置列表
     */
    void saveNotifyConfigs(Integer userId, List<UserNotifyConfigUpdateVO> userNotifyConfigUpdateVOS);

}
