package com.example.started.modules.table.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.started.modules.table.bo.add.ShowDataBo;
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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

/**
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class ShowServiceImpl implements ShowService {
    private final SysTableDao tableDao;
    private final SysTableFieldDao tableFieldDao;

    @Override
    public ShowEditVo tableInfo(String vp) {

        ShowEditVo showEditVo = new ShowEditVo();

        LambdaQueryWrapper<ShowTableEntity> queryWrapper = new LambdaQueryWrapper<ShowTableEntity>()
                .select(ShowTableEntity::getId, ShowTableEntity::getTableName)
                .eq(ShowTableEntity::getVp, vp);
        List<ShowTableEntity> showTableEntities = tableDao.selectList(queryWrapper);
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

    private List<ShowTableFieldEntity> getFieldByTableId(long tableId) {
        LambdaQueryWrapper<ShowTableFieldEntity> queryWrapper1 = new LambdaQueryWrapper<ShowTableFieldEntity>()
                .eq(ShowTableFieldEntity::getTableId, tableId);
        return tableFieldDao.selectList(queryWrapper1);
    }

    private List<ShowTableEntity> getTableByPid(long pid) {
        LambdaQueryWrapper<ShowTableEntity> queryWrapper1 = new LambdaQueryWrapper<ShowTableEntity>()
                .eq(ShowTableEntity::getPid, pid);
        return tableDao.selectList(queryWrapper1);
    }

    @Override
    public void add(String vp, List<ShowDataBo> addBody) {

        // 1. 先处理参数合集
        ShowDataTo mainTableData = new ShowDataTo();
        this.cl(addBody, mainTableData);

        LambdaQueryWrapper<ShowTableEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ShowTableEntity::getTableName);
        queryWrapper.eq(ShowTableEntity::getVp, vp);
        List<ShowTableEntity> showTableEntities = tableDao.selectList(queryWrapper);
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
            tableDao.insertData(e.getTableName(), col.toList(), data);
        });
    }

    @Override
    public ShowDataVo list(String vp, ShowQueryBo queryBo) {
        ShowDataVo showDataVo = new ShowDataVo();
        {
            LinkedList<ShowColumnsVo> fields = new LinkedList<>();
            fields.add(new ShowColumnsVo().setKey("key0").setDataKey("dk0").setTitle("标题0").setWidth(100));
            fields.add(new ShowColumnsVo().setKey("key1").setDataKey("dk1").setTitle("标题1").setWidth(100));
            fields.add(new ShowColumnsVo().setKey("key2").setDataKey("dk2").setTitle("标题2").setWidth(100));
            fields.add(new ShowColumnsVo().setKey("key3").setDataKey("dk3").setTitle("标题3").setWidth(100));
            showDataVo.setColumns(fields);
        }
        for (int i = 0; i < 50; i++) {
            HashMap<String, Object> map = new HashMap<>();
            for (int j = 0; j < 4; j++) {
                map.put("dk" + j, "value" + i + ", " + j);
            }
            showDataVo.getData().add(map);
        }
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
}
