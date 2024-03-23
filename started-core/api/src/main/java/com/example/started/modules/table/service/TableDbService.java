package com.example.started.modules.table.service;

import com.example.started.modules.table.bo.add.ShowDataBo;
import com.example.started.modules.table.bo.ShowQueryBo;
import com.example.started.modules.table.vo.edit.ShowEditVo;
import com.example.started.modules.table.vo.list.ShowDataVo;

import java.util.List;

/**
 * 表相关的
 *
 * @since 1.0.0
 */
public interface TableDbService {
    /**
     * 显示表格详情
     *
     * @param vp 虚拟路径
     * @return .
     */
    ShowEditVo tableInfo(String vp);

    void saveData(String vp, List<ShowDataBo> addBody);

    ShowDataVo list(String vp, ShowQueryBo queryBo);
}
