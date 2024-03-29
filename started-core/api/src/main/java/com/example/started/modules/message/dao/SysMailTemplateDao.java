package com.example.started.modules.message.dao;

import com.example.started.modules.message.entity.SysMailTemplateEntity;
import com.example.common.v0.data.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件模板
 */
@Mapper
public interface SysMailTemplateDao extends BaseDao<SysMailTemplateEntity> {

}
