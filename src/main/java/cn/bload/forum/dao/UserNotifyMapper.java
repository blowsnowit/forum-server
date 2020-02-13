package cn.bload.forum.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import cn.bload.forum.entity.dto.UserNotifyDTO;
import cn.bload.forum.entity.query.NotifyQuery;
import cn.bload.forum.model.UserNotify;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author blowsnow
 * @since 2020-02-12
 */
public interface UserNotifyMapper extends BaseMapper<UserNotify> {

    List<UserNotifyDTO> getNotifys(NotifyQuery notifyQuery);

    Integer getUnReadNotifyNum(Integer userId);

}
