package com.example.started.modules.auth.validate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVo {
    private String userName;
    /**
     * 显示名称
     */
    private String showName;
}
