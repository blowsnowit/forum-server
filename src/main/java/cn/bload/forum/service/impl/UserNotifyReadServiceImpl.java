package cn.bload.forum.service.impl;

import cn.bload.forum.model.UserNotifyRead;
import cn.bload.forum.dao.UserNotifyReadMapper;
import cn.bload.forum.service.UserNotifyReadService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author blowsnow
 * @since 2020-02-12
 */
@Service("userNotifyReadService")
public class UserNotifyReadServiceImpl extends ServiceImpl<UserNotifyReadMapper, UserNotifyRead> implements UserNotifyReadService {
    @Resource
    UserNotifyReadMapper userNotifyReadMapper;

}
