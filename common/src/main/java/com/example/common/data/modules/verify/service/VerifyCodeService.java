package com.example.common.data.modules.verify.service;

import com.example.common.data.bo.VerifyCodeBo;
import com.example.common.data.dto.VerifyCodeDTO;
import com.example.common.data.entity.VerifyCodeEntity;
import com.example.common.data.service.CrudService;

/**
 * 验证码
 *
 * @since 1.0.0 2020-07-06
 */
public interface VerifyCodeService extends CrudService<VerifyCodeBo, VerifyCodeEntity, VerifyCodeDTO> {

    void send(String loginName);

    boolean checkCode(String code, String account);
}
