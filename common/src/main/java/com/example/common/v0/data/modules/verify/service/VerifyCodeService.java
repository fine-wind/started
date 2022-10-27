package com.example.common.v0.data.modules.verify.service;

import com.example.common.v0.data.bo.VerifyCodeBo;
import com.example.common.v0.data.dto.VerifyCodeDTO;
import com.example.common.v0.data.entity.VerifyCodeEntity;
import com.example.common.v0.data.service.CrudService;

/**
 * 验证码
 *
 * @since 1.0.0 2020-07-06
 */
public interface VerifyCodeService extends CrudService<VerifyCodeBo, VerifyCodeEntity, VerifyCodeDTO> {

    void send(String loginName);

    boolean checkCode(String code, String account);
}
