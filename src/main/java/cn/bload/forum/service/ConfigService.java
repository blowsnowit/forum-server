package cn.bload.forum.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

import cn.bload.forum.model.Config;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author blowsnow
 * @since 2020-02-08
 */
public interface ConfigService extends IService<Config> {

    /**
     * 通过配置类型获取其下的配置列表
     * @param type 配置类型
     * @return
     */
    List<Config> getConfigsByType(String type);

    /**
     * 获取指定key的配置
     * @param key 配置名称
     * @return
     */
    Config getConfig(String key);

    /**
     * 获取配置类型列表
     * @return
     */
    List<String> getConfigTypes();

    /**
     * 获取配置列表，关联关系
     * @return
     */
    Map<String,List<Config>> getConfigs();

    /**
     * 保存配置
     * @param configs
     */
    void saveConfigs(List<Config> configs);

}
