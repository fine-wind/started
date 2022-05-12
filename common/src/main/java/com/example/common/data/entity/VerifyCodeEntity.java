package com.example.common.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.example.common.data.entity.BaseEntity;
import lombok.experimental.Accessors;

/**
 * 验证码
 *
 * @since 1.0.0 2020-07-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("book_verify_code")
public class VerifyCodeEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private String mobile;
    /**
     *
     */
    private String loginName;
    /**
     *
     */
    private String code;
    /**
     *
     */
    private Integer state;
}
