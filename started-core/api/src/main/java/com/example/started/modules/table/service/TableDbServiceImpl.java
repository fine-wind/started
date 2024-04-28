package com.example.started.modules.table.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.v0.exception.ServerException;
import com.example.started.modules.table.bo.add.ShowDataBo;
import com.example.started.modules.table.dto.SysTableDto;
import com.example.started.modules.table.dto.SysTableFieldTypeEnum;
import com.example.started.modules.table.to.ShowDataTo;
import com.example.started.modules.table.bo.ShowQueryBo;
import com.example.started.modules.table.dao.SysTableDao;
import com.example.started.modules.table.dao.SysTableFieldDao;
import com.example.started.modules.table.entity.ShowTableEntity;
import com.example.started.modules.table.entity.ShowTableFieldEntity;
import com.example.started.modules.table.vo.edit.ShowEditChildFieldVo;
import com.example.started.modules.table.vo.edit.ShowEditVo;
import com.example.started.modules.table.vo.list.ShowColumnsVo;
import com.example.started.modules.table.vo.list.ShowDataVo;
import com.example.started.modules.table.vo.list.ShowOpVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

/**
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class TableDbServiceImpl implements TableDbService {
    private final SysTableDao sysTableDao;
    private final SysTableFieldDao tableFieldDao;

    private ShowTableEntity getTable(String vp) {
        LambdaQueryWrapper<ShowTableEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShowTableEntity::getVp, vp);
        ShowTableEntity showTableEntities = sysTableDao.selectOne(queryWrapper);
        if (Objects.isNull(showTableEntities)) {
            throw new ServerException("路径不正确");
        }
        return showTableEntities;
    }

    private List<ShowTableFieldEntity> getFieldByTableId(long tableId) {
        LambdaQueryWrapper<ShowTableFieldEntity> queryWrapper1 = new LambdaQueryWrapper<ShowTableFieldEntity>()
                .eq(ShowTableFieldEntity::getTableId, tableId)
                .orderByAsc(ShowTableFieldEntity::getSort);
        return tableFieldDao.selectList(queryWrapper1);
    }

    private List<ShowTableEntity> getTableByPid(long pid) {
        LambdaQueryWrapper<ShowTableEntity> queryWrapper1 = new LambdaQueryWrapper<ShowTableEntity>()
                .eq(ShowTableEntity::getPid, pid);
        return sysTableDao.selectList(queryWrapper1);
    }

    @Override
    public void saveData(String vp, List<ShowDataBo> addBody) {

        // 1. 先处理参数合集
        ShowDataTo mainTableData = new ShowDataTo();
        this.cl(addBody, mainTableData);

        LambdaQueryWrapper<ShowTableEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ShowTableEntity::getTableName);
        queryWrapper.eq(ShowTableEntity::getVp, vp);
        List<ShowTableEntity> showTableEntities = sysTableDao.selectList(queryWrapper);
        showTableEntities.forEach(e -> {
            LambdaQueryWrapper<ShowTableFieldEntity> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(ShowTableFieldEntity::getTableId, e.getId());
            List<ShowTableFieldEntity> showTableFieldEntities = tableFieldDao.selectList(queryWrapper1);
            Stream<String> col = showTableFieldEntities.stream().map(ShowTableFieldEntity::getField);
            LinkedList<List<String>> data = new LinkedList<>();
            LinkedList<String> mainDataValue = new LinkedList<>();
            data.add(mainDataValue);
            showTableFieldEntities.forEach(fieldEntity -> {
                String o = mainTableData.getBody().get(fieldEntity.getName());
                // todo 对数据进行校验
                mainDataValue.add(o);
            });
            // 入库
            sysTableDao.insertData(e.getTableName(), col.toList(), data);
        });
    }

    public List<ShowColumnsVo> operates(String vp, ShowQueryBo queryBo) {
        ShowTableEntity showTableEntity = this.getTable(vp);
        ShowDataVo showDataVo = new ShowDataVo();

        SysTableDto sysTableDto = new SysTableDto();
        sysTableDto.setTableName(showTableEntity.getTableName());
        this.getFieldByTableId(showTableEntity.getId()).forEach(f -> {
            /* 操作按钮*/
            if (SysTableFieldTypeEnum.op(f.getType())) {
                showDataVo.putOperate(new ShowOpVo(f.getTitle()));
            }
        });
        return null;
    }

    /**
     * 页面显示的表头
     */
    @Override
    public List<ShowColumnsVo> columns(String vp) {
        ShowTableEntity showTableEntity = this.getTable(vp);
        List<ShowColumnsVo> columnsVos = new LinkedList<>();

        SysTableDto sysTableDto = new SysTableDto();
        sysTableDto.setTableName(showTableEntity.getTableName());
        this.getFieldByTableId(showTableEntity.getId()).forEach(f -> {
            if (SysTableFieldTypeEnum.show(f)) {
                columnsVos.add(new ShowColumnsVo(f.getType(), f.getField(), f.getField(), f.getTitle(), 100));
            }
        });
        return columnsVos;
    }

    @Override
    public Long count(String vp, ShowQueryBo queryBo) {
        return 1000L;// sysTableDao.selectCount(null);
    }

    @Override
    public ShowDataVo list(String vp, ShowQueryBo queryBo) {
        ShowTableEntity showTableEntity = this.getTable(vp);
        ShowDataVo showDataVo = new ShowDataVo();

        SysTableDto sysTableDto = new SysTableDto();
        sysTableDto.setTableName(showTableEntity.getTableName());
        this.getFieldByTableId(showTableEntity.getId()).forEach(f -> {
            if (SysTableFieldTypeEnum.select(f.getType())) {
                sysTableDto.putField(f.getField());
            }
            if (SysTableFieldTypeEnum.op(f.getType())) {
                showDataVo.putOperate(new ShowOpVo(f.getTitle()));
            }
        });
        IPage<Map<String, Object>> maps = sysTableDao.selectTableDataPage(new Page<>(1, 10), sysTableDto);
//        List<ShowTableEntity> showTableEntities = sysTableDao.selectList(null);
        showDataVo.putRecords(maps);
        return showDataVo;
    }

    /**
     * 处理数据格式
     */
    private void cl(List<ShowDataBo> addBody, ShowDataTo showDataTo) {
        // 1. 先处理参数合集
        Map<String, String> body = showDataTo.getBody();
        addBody.forEach(e -> {
            if (Objects.nonNull(e.getChild()) && !e.getChild().isEmpty()) {
                ShowDataTo e1 = new ShowDataTo();
                this.cl(e.getChild(), e1);
                showDataTo.getChild().add(e1);
            }
            body.put(e.getName(), e.getValue());
        });
    }


    @Override
    public ShowEditVo tableInfo(String vp) {

        ShowEditVo showEditVo = new ShowEditVo();

        LambdaQueryWrapper<ShowTableEntity> queryWrapper = new LambdaQueryWrapper<ShowTableEntity>()
                .select(ShowTableEntity::getId, ShowTableEntity::getTableName)
                .eq(ShowTableEntity::getVp, vp);

        List<ShowTableEntity> showTableEntities = sysTableDao.selectList(queryWrapper);
        showTableEntities.forEach(e -> {
            // 当表字段
            this.getFieldByTableId(e.getId()).forEach(fieldEntity -> showEditVo.getFields().add(fieldEntity.toEditVo()));
            // 子表
            this.getTableByPid(e.getId()).forEach(c -> {
                ShowEditChildFieldVo e1 = new ShowEditChildFieldVo();
                e1.setTitle(c.getTitle());
                e1.setName(c.getName());
                this.getFieldByTableId(c.getId()).forEach(cf -> e1.getFields().add(cf.toEditVo()));
                showEditVo.getChildFields().add(e1);
            });
        });
        return showEditVo;
    }

}
