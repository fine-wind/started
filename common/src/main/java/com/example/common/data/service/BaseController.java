package com.example.common.data.service;

import com.example.common.data.bo.BaseBo;
import com.example.common.data.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基础控制接口
 */
public class BaseController<Bo extends BaseBo, T extends BaseEntity, S extends BaseService<Bo, T>> {

    /**
     * 自动注入service层
     */
    @Autowired
    private S baseService;


    public S getBaseService() {
        return baseService;
    }


}
