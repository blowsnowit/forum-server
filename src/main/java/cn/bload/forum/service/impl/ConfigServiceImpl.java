package cn.bload.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.sql.StringEscape;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.bload.forum.dao.ConfigMapper;
import cn.bload.forum.model.Config;
import cn.bload.forum.service.ConfigService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author blowsnow
 * @since 2020-02-08
 */
@Service("configService")
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {
    @Resource
    ConfigMapper configMapper;

    @Override
    @Cacheable(value = "cache_configs",key = "'cache_configs_type_' + #type")
    public List<Config> getConfigsByType(String type) {
        QueryWrapper<Config> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Config::getConfigType,type);
        return configMapper.selectList(queryWrapper);
    }

    @Override
    @Cacheable(value = "cache_configs",key = "'cache_configs_key_' + #key")
    public Config getConfig(String key) {
        QueryWrapper<Config> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Config::getConfigKey,key);
        return configMapper.selectOne(queryWrapper);
    }

    @Override
    @Cacheable(value = "cache_configs",key = "'cache_configs_types'")
    public List<String> getConfigTypes() {
        return configMapper.getConfigTypes();
    }

    @Override
    @Cacheable(value = "cache_configs",key = "'cache_configs'")
    public Map<String, List<Config>> getConfigs() {
        Map<String, List<Config>> map = new HashMap<>();
        List<String> configTypes = getConfigTypes();
        for (String configType : configTypes) {
            map.put(configType,getConfigsByType(configType));
        }
        return map;
    }

    @Override
    @CacheEvict(value = "cache_configs", allEntries=true)
    public void saveConfigs(List<Config> configs) {
        for (Config config : configs) {
            config.setConfigValue(StringEscape.escapeString(config.getConfigValue()));
            configMapper.saveConfig(config);
        }
    }

}
