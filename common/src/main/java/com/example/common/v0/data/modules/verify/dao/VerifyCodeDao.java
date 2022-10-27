package com.example.common.v0.data.modules.verify.dao;

import com.example.common.v0.data.dao.BaseDao;
import com.example.common.v0.data.entity.VerifyCodeEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 验证码
 *
 *
 * @since 1.0.0 2020-07-06
 */
@Mapper
public interface VerifyCodeDao extends BaseDao<VerifyCodeEntity> {

}
