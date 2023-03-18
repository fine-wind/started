package com.example.common.v1.base;

import com.example.common.v0.data.bo.BaseBo;
import com.example.common.v0.data.entity.BaseEntity;
import com.example.common.v0.data.service.BaseService;

/**
 * 基础控制接口
 */
public class BaseController<Bo extends BaseBo, T extends BaseEntity, S extends BaseService<Bo, T>> {

}
