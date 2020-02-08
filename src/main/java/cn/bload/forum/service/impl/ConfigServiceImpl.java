package cn.bload.forum.service.impl;

import cn.bload.forum.model.Config;
import cn.bload.forum.dao.ConfigMapper;
import cn.bload.forum.service.ConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

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

}
