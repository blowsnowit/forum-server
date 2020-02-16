package cn.bload.forum.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import cn.bload.forum.model.UserNotifyConfig;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author blowsnow
 * @since 2020-02-16
 */
public interface UserNotifyConfigMapper extends BaseMapper<UserNotifyConfig> {

    UserNotifyConfig getNotifyConfig(@Param("userId") Integer userId,@Param("value") String value);

    List<UserNotifyConfig> getNotifyConfigs(Integer userId);

}
