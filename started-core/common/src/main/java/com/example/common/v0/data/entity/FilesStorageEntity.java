package com.example.common.v0.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 文件存储配置信息
 *
 * @since 1.0.0 2020-06-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("book_files_storage")
public class FilesStorageEntity extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 环境（dev:开发,test:测试,prod:正式）
     */
    private String profile;
    /**
     * 存储路径
     */
    private String path;
}
