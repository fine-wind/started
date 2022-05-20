package com.example.modules.master.bo;

import com.example.common.data.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MasterUserBo extends BaseBo {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 状态
     */
    private Long status;

}
