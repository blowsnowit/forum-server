package cn.bload.forum.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import cn.bload.forum.entity.query.UserQuery;
import cn.bload.forum.model.User;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author blowsnow
 * @since 2020-01-18
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> getUserList(UserQuery userQuery);

}
