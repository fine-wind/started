package com.example.started.modules.auth.server.sys.user;

import com.example.started.common.constant.PageBo;
import com.example.started.common.v0.utils.PageUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
public class AuthUserAllBo {
    private PageBo page;
    /**
     * 用户名
     */
    private String username;
}
