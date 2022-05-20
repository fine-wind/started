package com.example.modules.message.dao;

import com.example.common.data.dao.BaseDao;
import com.example.modules.message.entity.SysMailTemplateEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件模板
 */
@Mapper
public interface SysMailTemplateDao extends BaseDao<SysMailTemplateEntity> {

}
