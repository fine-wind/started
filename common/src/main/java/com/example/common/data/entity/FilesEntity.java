package com.example.common.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 文件信息表
 *
 *
 * @since 1.0.0 2020-06-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("book_files")
public class FilesEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 文件名
     */
    private String filename;
    /**
     * 文件大小
     */
    private Long filesize;
    /**
     * 文件类别
     */
    private String filetype;
    /**
     * 磁盘存储名字
     */
    private String storename;
    /**
     * 是否删除 0:未删除,1:已删除
     */
    private String flag;
    /**
     * 磁盘存储ID
     */
    private Long filestorageid;
    /**
     * 上传人
     */
    private String uploaduser;
    /**
     * 上传时间
     */
    private Date uploaddate;
    /**
     * 删除人
     */
    private String deleteuser;
    /**
     * 删除时间
     */
    private Date deletedate;
    /**
     * 存储文件夹
     */
    private String filedirpath;
}
