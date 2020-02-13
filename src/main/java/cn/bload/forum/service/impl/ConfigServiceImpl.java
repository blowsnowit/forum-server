package cn.bload.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Cacheable(value = "cache_configs",key = "'cache_configs_' + #type")
    public List<Config> getConfigsByType(String type) {
        QueryWrapper<Config> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Config::getConfigType,type);
        return configMapper.selectList(queryWrapper);
    }

    @Override
    public Config getConfig(String key) {
        QueryWrapper<Config> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Config::getConfigKey,key);
        return configMapper.selectOne(queryWrapper);
    }

    //TODO 保存配置，记得清楚缓存
}
