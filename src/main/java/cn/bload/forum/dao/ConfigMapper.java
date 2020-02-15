package cn.bload.forum.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import cn.bload.forum.model.Config;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author blowsnow
 * @since 2020-02-08
 */
public interface ConfigMapper extends BaseMapper<Config> {

    List<String> getConfigTypes();

    void saveConfigs(@Param("configs") List<Config> configs);

    void saveConfig(Config config);
}
