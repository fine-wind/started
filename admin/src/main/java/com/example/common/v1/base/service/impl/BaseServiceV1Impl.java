package com.example.common.v1.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.bo.PageBo;
import com.example.common.v0.data.dao.BaseDao;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.utils.ConvertUtils;
import com.example.common.v0.utils.StringUtil;
import com.example.common.v1.base.BaseDto;
import com.example.common.v1.base.BaseEntity;
import com.example.common.v1.base.SelectPageUtil;
import com.example.common.v1.base.service.BaseServiceV1;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 基础服务类
 *
 * @param <DTO> 查询参数类
 * @param <DAO> 持久层
 * @param <T>   实体类
 */
public class BaseServiceV1Impl<DTO extends BaseDto, DAO extends BaseDao<T>, T extends BaseEntity> implements BaseServiceV1<DTO, T> {
    @Autowired
    protected DAO baseDao;

    protected Class<T> currentEntityClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(BaseServiceV1Impl.class, getClass(), 2);
    }

    protected Class<DTO> currentDtoClass() {
        return (Class<DTO>) ReflectionKit.getSuperClassGenericType(this.getClass(), getClass(), 0);
    }
    // region c

    @Override
    public boolean insert(DTO dto) {
        T entity = ConvertUtils.sourceToTarget(dto, currentEntityClass());
        int insert = baseDao.insert(entity);
        dto.setId(entity.getId());
        return BaseServiceV1Impl.retBool(insert);
    }

    @Override
    public boolean insert(T entity) {
        return BaseServiceV1Impl.retBool(baseDao.insert(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertBatch(Collection<T> entityList) {
        return this.insertBatch(entityList, 100);
    }

    /**
     * 批量插入
     *
     * @param entityList 插入的数据
     * @param batchSize  每次插入的数量
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertBatch(Collection<T> entityList, int batchSize) {
        SqlSession batchSqlSession = sqlSessionBatch();
        int i = 0;
        String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
        try {
            for (T anEntityList : entityList) {
                batchSqlSession.insert(sqlStatement, anEntityList);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        } finally {
            closeSqlSession(batchSqlSession);
        }
        return true;
    }
    // endregion


    // region u

    @Override
    public boolean updateById(DTO dto) {
        T entity = ConvertUtils.sourceToTarget(dto, currentEntityClass());
        return BaseServiceV1Impl.retBool(baseDao.updateById(entity));
    }

    @Override
    public boolean updateById(T entity) {
        return BaseServiceV1Impl.retBool(baseDao.updateById(entity));
    }

    @Override
    public boolean update(T entity, Wrapper<T> updateWrapper) {

        return BaseServiceV1Impl.retBool(baseDao.update(entity, updateWrapper));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatchById(Collection<T> entityList) {
        return updateBatchById(entityList, 30);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
        SqlSession batchSqlSession = sqlSessionBatch();
        int i = 0;
        String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
        try {
            for (T anEntityList : entityList) {
                MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                param.put(Constants.ENTITY, anEntityList);
                batchSqlSession.update(sqlStatement, param);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        } finally {
            closeSqlSession(batchSqlSession);
        }
        return true;
    }
    // endregion

    // region r

    @Override
    public LambdaQueryWrapper<T> getQueryWrapper(DTO params) {

//        (Class<T>) params.getClass()
        //指定实体类类型
        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(StringUtil.isNotEmpty(params.getId()), T::getId, params.getId());
        wrapper.eq(params.getCreator() != null, T::getCreator, params.getCreator());

        // Boolean superMan = null;

        /* 普通情况*/
        // todo xing 列权限管理
        // wrapper.select(T::getId);
        // wrapper.select(T::getCreateDate);
        // endregion

        return wrapper;
    }


    protected <D> PageData<D> getPageData(IPage<T> page, Class<D> target) {
        return getPageData(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords(), target);
    }

    /**
     * @param list    .
     * @param total   .
     * @param current .
     * @param limit   .
     * @param target  .
     * @param <D>     .
     * @return .
     */
    protected <D> PageData<D> getPageData(long current, long limit, long total, List<?> list, Class<D> target) {
        List<D> targetList = ConvertUtils.sourceToTarget(list, target);

        return new PageData<>(current, limit, total, targetList);
    }


    /**
     * 判断数据库操作是否成功
     * 注意！！ 该方法为 Integer 判断，不可传入 int 基本类型
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    protected static boolean retBool(Integer result) {
        return SqlHelper.retBool(result);
    }


    /**
     * 批量操作 SqlSession
     */
    protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(currentEntityClass());
    }

    /**
     * 释放sqlSession
     *
     * @param sqlSession session
     */
    protected void closeSqlSession(SqlSession sqlSession) {
        SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(currentEntityClass()));
    }

    /**
     * 获取SqlStatement
     *
     * @param sqlMethod sql方法
     * @return SqlStatement
     */
    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(currentEntityClass()).getSqlStatement(sqlMethod.getMethod());
    }


    @Override
    public T selectById(Serializable id) {
        return baseDao.selectById(id);
    }

    @Override
    public Long count(LambdaQueryWrapper<T> lambdaQueryWrapper) {
        return baseDao.selectCount(lambdaQueryWrapper);
    }

    /**
     * 列表
     *
     * @param params 参数
     * @return 符合条件的列表
     */
    @Override
    public List<DTO> selectList(DTO params) {
        List<T> es = baseDao.selectList(getQueryWrapper(params));
        return ConvertUtils.sourceToTarget(es, currentDtoClass());
    }

    /**
     * 分页
     *
     * @param params 条件参数
     * @param pageBo 分页参数
     * @return 分页列表
     */
    @Override
    public PageData<DTO> selectPage(DTO params, PageBo pageBo) {
        IPage<T> page = SelectPageUtil.getPage(params, pageBo, StringUtil.toString(pageBo.getOrderField()), Constant.TABLE_ORDER.ASC.equals(pageBo.getOrder()));
        baseDao.selectPage(page, getQueryWrapper(params));
        IPage<T> data = baseDao.selectPage(page, getQueryWrapper(params));
        return getPageData(data, currentDtoClass());
    }

    // endregion

    // region d
    @Override
    public boolean deleteById(Serializable id) {
        return SqlHelper.retBool(baseDao.deleteById(id));
    }

    @Override
    public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
        return SqlHelper.retBool(baseDao.deleteBatchIds(idList));
    }
    // endregion


}
