package com.example.common.v0.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.bo.BaseBo;
import com.example.common.v0.data.bo.PageBo;
import com.example.common.v0.data.dao.BaseDao;
import com.example.common.v0.data.entity.BaseEntity;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.BaseService;
import com.example.common.v0.utils.ConvertUtils;
import com.example.common.v0.utils.StringUtil;
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
 * @param <Bo>  查询参数类
 * @param <Dao> 持久层
 * @param <E>   实体类
 */
@Deprecated
public abstract class BaseServiceImpl<Bo extends BaseBo, Dao extends BaseDao<E>, E extends BaseEntity>
        implements BaseService<Bo, E> {
    @Autowired
    protected Dao baseDao;

    @SuppressWarnings("resource")
    protected Class<E> currentModelClass() {
        return (Class<E>) ReflectionKit.getSuperClassGenericType(BaseServiceImpl.class, getClass(), 2);
    }
    // region c

    @Override
    public boolean insert(E entity) {

        return BaseServiceImpl.retBool(baseDao.insert(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertBatch(Collection<E> entityList) {
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
    public boolean insertBatch(Collection<E> entityList, int batchSize) {
        SqlSession batchSqlSession = sqlSessionBatch();
        int i = 0;
        String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
        try {
            for (E anEntityList : entityList) {
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
    public boolean updateById(E entity) {
        return BaseServiceImpl.retBool(baseDao.updateById(entity));
    }

    @Override
    public boolean update(E entity, Wrapper<E> updateWrapper) {

        return BaseServiceImpl.retBool(baseDao.update(entity, updateWrapper));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatchById(Collection<E> entityList) {
        return updateBatchById(entityList, 30);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatchById(Collection<E> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
        SqlSession batchSqlSession = sqlSessionBatch();
        int i = 0;
        String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
        try {
            for (E anEntityList : entityList) {
                MapperMethod.ParamMap<E> param = new MapperMethod.ParamMap<>();
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
    public LambdaQueryWrapper<E> getQueryWrapper(Bo params) {

//        (Class<T>) params.getClass()
        //指定实体类类型
        LambdaQueryWrapper<E> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(params.getId() != null, E::getId, params.getId());
        wrapper.eq(params.getCreator() != null, E::getCreator, params.getCreator());

        // Boolean superMan = null;

        /* 普通情况*/
        // todo xing 列权限管理
        // wrapper.select(T::getId);
        // wrapper.select(T::getCreateDate);
        // endregion

        return wrapper;
    }

    /**
     * 获取分页对象
     *
     * @param params 分页查询参数
     */
    protected IPage<E> getPage(Bo params) {
        return this.getPage(params, null, true);
    }

    protected IPage<E> getPage(Bo params, String defaultOrderField, boolean isAsc) {
        PageBo page = params.getPage();
        //分页参数及默认值
        page.setPage(params.getPage() == null ? 1 : page.getPage());
        page.setLimit(page.getLimit() == null ? Constant.PAGE.LIMIT_NUMBER : page.getLimit());

        //分页对象
        Page<E> tPage = new Page<>(page.getPage(), page.getLimit());

        //排序字段
        String orderField = page.getOrderField();
        String order = page.getOrder();

        //前端字段排序
        if (StringUtil.isNotEmpty(orderField) && StringUtil.isNotEmpty(order)) {
            orderField = StringUtil.humpTo_(orderField);
            if (Constant.TABLE_ORDER.ASC.equalsIgnoreCase(order)) {
                return tPage.addOrder(OrderItem.asc(orderField));
            } else {
                return tPage.addOrder(OrderItem.desc(orderField));
            }
        }

        //没有排序字段，则不排序
        if (StringUtil.isEmpty(defaultOrderField)) {
            return tPage;
        }

        //默认排序
        if (isAsc) {
            tPage.addOrder(OrderItem.asc(defaultOrderField));
        } else {
            tPage.addOrder(OrderItem.desc(defaultOrderField));
        }

        return tPage;
    }

    protected <D> PageData<D> getPageData(IPage<E> page, Class<D> target) {
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
        return SqlHelper.sqlSessionBatch(currentModelClass());
    }

    /**
     * 释放sqlSession
     *
     * @param sqlSession session
     */
    protected void closeSqlSession(SqlSession sqlSession) {
        SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(currentModelClass()));
    }

    /**
     * 获取SqlStatement
     *
     * @param sqlMethod sql方法
     * @return SqlStatement
     */
    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }


    @Override
    public E selectById(Serializable id) {
        return baseDao.selectById(id);
    }

    @Override
    public Long count(LambdaQueryWrapper<E> lambdaQueryWrapper) {
        return baseDao.selectCount(lambdaQueryWrapper);
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
