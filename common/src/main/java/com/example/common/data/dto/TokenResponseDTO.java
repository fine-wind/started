package com.example.common.data.dto;

import com.example.common.constant.Constant;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 用户token
 * <p>
 * email xingii@outlook.com
 * datetime 2020-8-19 10:53
 *
 * @author 行星
 */
@Data
@ApiModel(value = Constant.REQUEST.HEADER.TOKEN)
public class TokenResponseDTO {
    private String token;
    /**
     * 到多少毫秒失效
     */
    private long expire;

    public TokenResponseDTO(String token, Long userid, long expire) {
        this.token = token;
        this.expire = expire;
    }
}
