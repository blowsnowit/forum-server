package cn.bload.forum.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import cn.bload.forum.model.UserNotifyRead;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author blowsnow
 * @since 2020-02-12
 */
public interface UserNotifyReadMapper extends BaseMapper<UserNotifyRead> {

    void readNotifyByIds(@Param("userId") Integer userId,@Param("ids") List<Integer> ids);
}
