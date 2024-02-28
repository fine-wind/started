package com.example.started.auth.modules.userConf.bo;

import com.example.common.v0.data.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户个性化配置
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UserConfBo extends BaseBo {
    private static final long serialVersionUID = 1L;
    /**
     * 配置项
     */
    private Long userId;
    /**
     * 配置项
     */
    private String item;
    /**
     * 配置项内容
     */
    private String content;

    /**
     * 配置状态
     *
     * @see
     */
    private Integer status;

}