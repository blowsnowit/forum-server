package cn.bload.forum.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    List<Config> getConfigsByType(String type);

    Config getConfig(String key);
}
