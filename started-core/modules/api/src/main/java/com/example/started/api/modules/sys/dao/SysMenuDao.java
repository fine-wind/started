package com.example.started.api.modules.sys.dao;

import com.example.started.api.modules.sys.entity.SysResourcesEntity;
import com.example.common.v0.data.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单管理
 */
@Mapper
public interface SysMenuDao extends BaseDao<SysResourcesEntity> {


    /**
     * 查询用户菜单列表
     *
     * @param userId   用户ＩＤ
     * @param type     菜单类型
     * @param language 语言
     */
    List<SysResourcesEntity> getUserMenuList(@Param("userId") String userId, @Param("type") Integer type, @Param("language") String language);


}
