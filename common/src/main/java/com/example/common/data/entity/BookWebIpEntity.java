package com.example.common.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 访问管理
 *
 * @since 1.0.0 2020-06-22
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("book_web_ip")
public class BookWebIpEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 开始ip
     */
    private String startIp;
    /**
     * 结束ip
     */
    private String endIp;
    /**
     * 标志位
     */
    private String flag;
}
