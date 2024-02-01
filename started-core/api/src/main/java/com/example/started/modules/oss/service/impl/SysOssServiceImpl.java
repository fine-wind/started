package com.example.started.modules.oss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.started.modules.oss.bo.SysOssBo;
import com.example.started.modules.oss.dao.SysOssDao;
import com.example.started.modules.oss.entity.SysOssEntity;
import com.example.started.modules.oss.service.SysOssService;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.impl.BaseServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.Objects;

import static com.example.common.v0.constant.Constant.TABLE.CREATE_DATE;

@Log4j2
@Service
public class SysOssServiceImpl extends BaseServiceImpl<SysOssBo, SysOssDao, SysOssEntity> implements SysOssService {

    @Override
    public PageData<SysOssEntity> page(SysOssBo params) {
        IPage<SysOssEntity> page = baseDao.selectPage(
                getPage(params, CREATE_DATE, false),
                new QueryWrapper<>()
        );
        return getPageData(page, SysOssEntity.class);
    }

    /**
     * 保存一个对象到数据库
     * TODO 对象标识符不够随机，易被他人恶意访问
     *
     * @param obj 对象
     * @return 对象标识符
     */
    @Override
    public String saveOneImgObjToDb(SysOssBo obj) {
        SysOssEntity sysOssEntity = new SysOssEntity();
        BeanUtils.copyProperties(obj, sysOssEntity);

        this.insert(sysOssEntity);
        return sysOssEntity.getId().toString();
    }

    @Override
    public File getFileId(String fileid) {
        // TODO 对象标识符
        SysOssEntity fileEntity = baseDao.selectById(fileid);
        if (Objects.isNull(fileEntity)) {
            return null;
        }
        String filedirpath = fileEntity.getUrl();
        String cacheFileName = null;
        if (filedirpath.startsWith("http:") || filedirpath.startsWith("https:")) {
            // 先判断本地是否存在缓存
            cacheFileName = System.getProperty("java.io.tmpdir") + "/cache/" + fileid;
            File file = new File(cacheFileName);
            if (!file.exists()) {
                try {
                    URL url = new URL(filedirpath);
                    DataInputStream dataInputStream = new DataInputStream(url.openStream());

                    // 缓存到本地
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    ByteArrayOutputStream output = new ByteArrayOutputStream();

                    byte[] buffer = new byte[1024];
                    int length;

                    while ((length = dataInputStream.read(buffer)) > 0) {
                        output.write(buffer, 0, length);
                    }
                    // byte[] context = output.toByteArray();
                    fileOutputStream.write(output.toByteArray());
                    dataInputStream.close();
                    fileOutputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e.fillInStackTrace());
                }
            }
        }
        File file = new File(Objects.isNull(cacheFileName) ? filedirpath : cacheFileName);
        if (file.exists()) {
            return file;
        }
        return null;
    }
}
