package com.example.common.v1.base.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.v0.data.bo.PageBo;
import com.example.common.v0.data.page.PageData;
import com.example.common.v1.base.BaseDto;
import com.example.common.v1.base.BaseEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 基础服务接口，所有Service接口都要继承
 */
public interface BaseServiceV1<DTO extends BaseDto, T extends BaseEntity> {
    // region create

    /**
     * 插入一条记录（选择字段，策略插入）
     *
     * @param entity 实体对象
     */
    boolean insert(DTO entity);

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


    // region update

    /**
     * 根据 ID 选择修改
     *
     * @param entity 实体对象
     */
    boolean updateById(DTO entity);
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


    // region read


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
    LambdaQueryWrapper<T> getQueryWrapper(DTO params);

    /**
     * 查询符合条件的数据总量
     *
     * @param lambdaQueryWrapper 条件
     * @return 。
     */
    Long count(LambdaQueryWrapper<T> lambdaQueryWrapper);

    /**
     * 列表
     *
     * @param params 参数
     * @return 符合条件的列表
     */
    List<DTO> selectList(DTO params);

    /**
     * 分页
     *
     * @param params 参数
     * @return 分页列表
     */
    PageData<DTO> selectPage(DTO params, PageBo pageBo);

    // endregion


    // region delete

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
