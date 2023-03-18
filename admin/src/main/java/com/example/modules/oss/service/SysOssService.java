package com.example.modules.oss.service;

import com.example.common.v0.data.page.PageData;
import com.example.common.sys.oss.entity.SysOssEntity;
import com.example.common.v0.data.service.BaseService;
import com.example.modules.oss.bo.SysOssBo;

import java.io.File;

/**
 * 文件上传
 */
public interface SysOssService extends BaseService<SysOssBo, SysOssEntity> {

    PageData<SysOssEntity> page(SysOssBo params);

    /**
     * 保存一个对象到数据库
     *
     * @param obj 对象
     * @return 对象标识符
     */
    String saveOneImgObjToDb(SysOssBo obj);

    /**
     * 根据文件id获取文件
     *
     * @param fileid 文件id
     * @return 文件
     */
    File getFileId(String fileid);
}
