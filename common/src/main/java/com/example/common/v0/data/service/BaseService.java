package com.example.common.v0.data.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.v0.data.bo.BaseBo;
import com.example.common.v0.data.entity.BaseEntity;

import java.io.Serializable;
import java.util.Collection;

/**
 * 基础服务接口，所有Service接口都要继承
 */
@Deprecated
public interface BaseService<Bo extends BaseBo, T extends BaseEntity> {

    // region c

    /**
     * 插入一条记录（选择字段，策略插入）
     *
     * @param entity 实体对象
     */
    boolean insert(T entity);

    /**
     * 插入（批量），该方法不支持 Oracle、SQL Server
     *
     * @param entityList 实体对象集合
     */
    boolean insertBatch(Collection<T> entityList);

    /**
     * 插入（批量），该方法不支持 Oracle、SQL Server
     *
     * @param entityList 实体对象集合
     * @param batchSize  插入批次数量
     */
    boolean insertBatch(Collection<T> entityList, int batchSize);
    // endregion


    // region u

    /**
     * 根据 ID 选择修改
     *
     * @param entity 实体对象
     */
    boolean updateById(T entity);

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象
     * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     */
    boolean update(T entity, Wrapper<T> updateWrapper);

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     */
    boolean updateBatchById(Collection<T> entityList);

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     * @param batchSize  更新批次数量
     */
    boolean updateBatchById(Collection<T> entityList, int batchSize);

    // endregion


    // region r

    /**
     * 查询符合条件的数据总量
     *
     * @param lambdaQueryWrapper 条件
     * @return 。
     */
    Long count(LambdaQueryWrapper<T> lambdaQueryWrapper);

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    T selectById(Serializable id);

    /**
     * 根据参数 生成查询条件
     *
     * @param params 参数
     * @return 数据库查询条件
     */
    LambdaQueryWrapper<T> getQueryWrapper(Bo params);

    // endregion


    // region d

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    boolean deleteById(Serializable id);

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表
     */
    boolean deleteBatchIds(Collection<? extends Serializable> idList);
    // endregion

}
