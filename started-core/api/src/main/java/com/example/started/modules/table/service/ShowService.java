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
public interface ShowService {
    ShowEditVo tableInfo(String vp);

    void add(String vp, List<ShowDataBo> addBody);

    ShowDataVo list(String vp, ShowQueryBo queryBo);
}
