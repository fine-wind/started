package com.example.common.v0.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.example.common.v0.data.bo.BaseBo;
import com.example.common.v0.data.dao.BaseDao;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.entity.BaseEntity;
import com.example.common.v0.data.service.CrudService;
import com.example.common.v0.exception.ServerException;
import com.example.common.v0.exception.UniversalCode;
import com.example.common.v0.utils.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import com.example.common.v0.utils.StringUtil;

import java.util.Arrays;
import java.util.List;

/**
 * CRUD基础服务类
 *
 * @param <Bo>     请求参数
 * @param <Dao>    持久化类
 * @param <Entity> 实体类
 * @param <Dto>    dto
 */
@Slf4j
public abstract class CrudServiceImpl<Bo extends BaseBo, Dao extends BaseDao<Entity>, Entity extends BaseEntity, Dto>
        extends BaseServiceImpl<Bo, Dao, Entity>
        implements CrudService<Bo, Entity, Dto> {

    protected Class<Bo> currentBoClass() {
        return (Class<Bo>) ReflectionKit.getSuperClassGenericType(this.getClass(), CrudService.class, 0);
    }

    protected Class<Dto> currentDtoClass() {
        return (Class<Dto>) ReflectionKit.getSuperClassGenericType(this.getClass(), CrudService.class, 2);
    }

    @Override
    public PageData<Dto> selectPage(Bo params) {

        IPage<Entity> page = baseDao.selectPage(
                getPage(params, StringUtil.toString(params.getPage().getOrderField()), Constant.TABLE_ORDER.ASC.equals(params.getPage().getOrder())),
                getQueryWrapper(params)
        );

        return getPageData(page, currentDtoClass());
    }

    @Override
    public List<Dto> list(Bo params) {
        List<Entity> entityList = baseDao.selectList(this.getQueryWrapper(params));

        return ConvertUtils.sourceToTarget(entityList, currentDtoClass());
    }

    @Override
    public List<Dto> list(LambdaQueryWrapper<Entity> lambdaQueryWrapper) {
        List<Entity> entityList = baseDao.selectList(lambdaQueryWrapper);

        return ConvertUtils.sourceToTarget(entityList, currentDtoClass());
    }

    @Override
    public Dto getById(Long id) {
        Entity entity = this.selectById(id);

        return ConvertUtils.sourceToTarget(entity, currentDtoClass());
    }

    @Override
    public void save(Dto dto) {
        Entity entity = ConvertUtils.sourceToTarget(dto, currentModelClass());
        this.insert(entity);

        //copy主键值到dto
        BeanUtils.copyProperties(entity, dto);
    }

    @Override
    public void update(Dto dto) {
        Entity entity = ConvertUtils.sourceToTarget(dto, currentModelClass());
        this.updateById(entity);
    }

    /**
     * 按创建人和id更新
     *
     * @param dto     dto
     * @param creator 条件
     */
    public void update(Dto dto, long creator) {
        Entity entity = ConvertUtils.sourceToTarget(dto, currentModelClass());
        if (entity.getId() == null) {
            throw new ServerException(UniversalCode.NOT_NULL, "参数不正确");
        }
        Class<Bo> boClass = currentBoClass();
        Bo params = null;
        try {
            params = boClass.newInstance();

        } catch (InstantiationException | IllegalAccessException e) {
            log.error("convert error ", e);
        }
        params.setId(entity.getId());
        params.setCreator(creator);

        if (!this.update(entity, this.getQueryWrapper(params))) {
            throw new ServerException(UniversalCode.INTERNAL_SERVER_ERROR, "更新失败，请检查数据");
        }

    }

    @Override
    public void deleteByIds(Long[] ids) {
        this.deleteBatchIds(Arrays.asList(ids));
    }
}
