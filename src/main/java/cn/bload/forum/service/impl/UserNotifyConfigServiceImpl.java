package cn.bload.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.bload.forum.dao.UserNotifyConfigMapper;
import cn.bload.forum.entity.vo.UserNotifyConfigUpdateVO;
import cn.bload.forum.model.UserNotifyConfig;
import cn.bload.forum.service.UserNotifyConfigService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author blowsnow
 * @since 2020-02-16
 */
@Service("userNotifyConfigService")
public class UserNotifyConfigServiceImpl extends ServiceImpl<UserNotifyConfigMapper, UserNotifyConfig> implements UserNotifyConfigService {
    @Resource
    UserNotifyConfigMapper userNotifyConfigMapper;


    @Override
    public UserNotifyConfig getNotifyConfig(Integer userId, String value) {
        return userNotifyConfigMapper.getNotifyConfig(userId,value);
    }


    @Override
    public UserNotifyConfig getRealNotifyConfig(Integer userId, String name) {
        QueryWrapper<UserNotifyConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserNotifyConfig::getUserNotifyConfigName,name)
                .eq(UserNotifyConfig::getUserId,userId);
        return userNotifyConfigMapper.selectOne(queryWrapper);
    }

    @Override
    @Cacheable(value = "cache_notify_config",key = "'cache_notify_configs_'+ #userId")
    public List<UserNotifyConfig> getNotifyConfigs(Integer userId) {
        return userNotifyConfigMapper.getNotifyConfigs(userId);
    }

    @Override
    @Caching(evict = {
            //删除指定用户的缓存配置
            @CacheEvict(value = "cache_notify_config",key = "'cache_notify_configs_'+ #userId",condition = "#userId != 0"),
            //删除所有用户的缓存配置，因为修改了通用配置
            @CacheEvict(value = "cache_notify_config",condition = "#userId == 0")
    })
    @Transactional(rollbackFor = Exception.class)
    public void saveNotifyConfigs(Integer userId, List<UserNotifyConfigUpdateVO> userNotifyConfigUpdateVOS) {
        //获取通用配置
        List<UserNotifyConfig> notifyConfigs = getNotifyConfigs(0);

        Map<String,UserNotifyConfig> commonNotifyConfigMap = new HashMap<>();
        for (UserNotifyConfig notifyConfig : notifyConfigs) {
            commonNotifyConfigMap.put(notifyConfig.getUserNotifyConfigName(),notifyConfig);
        }

        for (UserNotifyConfigUpdateVO userNotifyConfigUpdateVO : userNotifyConfigUpdateVOS) {
            if (commonNotifyConfigMap.containsKey(userNotifyConfigUpdateVO.getUserNotifyConfigName())){
                UserNotifyConfig commonNotifyConfig = commonNotifyConfigMap.get(userNotifyConfigUpdateVO.getUserNotifyConfigName());


                UserNotifyConfig userNotifyConfig = new UserNotifyConfig();
                userNotifyConfig.setUserId(userId);
                userNotifyConfig.setUserNotifyConfigName(commonNotifyConfig.getUserNotifyConfigName());
                userNotifyConfig.setUserNotifyConfigValue(commonNotifyConfig.getUserNotifyConfigValue());
                userNotifyConfig.setUserNotifyConfigNotify(userNotifyConfigUpdateVO.getUserNotifyConfigNotify());
                userNotifyConfig.setUserNotifyConfigEmail(userNotifyConfigUpdateVO.getUserNotifyConfigEmail());

                //通过 userid + notifyname
                UserNotifyConfig realNotifyConfig = getRealNotifyConfig(userId, userNotifyConfig.getUserNotifyConfigName());
                //找不到就新增
                if (realNotifyConfig == null){
                    userNotifyConfigMapper.insert(userNotifyConfig);
                }else{
                    //找得到就更新
                    userNotifyConfig.setUserNotifyConfigId(realNotifyConfig.getUserNotifyConfigId());
                    userNotifyConfigMapper.updateById(userNotifyConfig);
                }
            }
        }
    }
}
