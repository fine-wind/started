package com.example.common.data.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.data.bo.BaseBo;
import com.example.common.data.entity.BaseEntity;
import com.example.common.data.page.PageData;

import java.util.List;

/**
 * CRUD基础服务接口
 */
public interface CrudService<Bo extends BaseBo, Entity extends BaseEntity, Dto> extends BaseService<Bo, Entity> {


    /**
     * 列表
     *
     * @param params 参数
     * @return 符合条件的列表
     */
    List<Dto> list(Bo params);

    /**
     * 分页
     *
     * @param params 参数
     * @return 分页列表
     */
    PageData<Dto> selectPage(Bo params);

    @Deprecated
    List<Dto> list(LambdaQueryWrapper<Entity> lambdaQueryWrapper);

    Dto getById(Long id);

    void save(Dto dto);

    void update(Dto dto);

    /**
     * 按创建人和id更新
     *
     * @param dto     dto
     * @param creator 条件
     */
    void update(Dto dto, long creator);

    void deleteByIds(Long[] ids);

}
