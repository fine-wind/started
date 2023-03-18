package com.example.modules.oss.controller;

import com.example.common.v0.exception.ServerException;
import com.example.common.v0.utils.StringUtil;
import com.example.modules.oss.bo.SysOssBo;
import com.example.modules.oss.cloud.AbstractCloudStorageService;
import com.example.modules.oss.cloud.CloudStorageConfig;
import com.example.modules.oss.cloud.OSSFactory;
import com.google.gson.Gson;
import com.example.common.v0.annotation.LogOperation;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.exception.UniversalCode;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.validator.ValidatorUtils;
import com.example.common.v0.validator.group.AliyunGroup;
import com.example.common.v0.validator.group.QcloudGroup;
import com.example.common.v0.validator.group.QiniuGroup;
import com.example.common.sys.oss.entity.SysOssEntity;
import com.example.modules.oss.service.SysOssService;
import com.example.modules.param.service.SysParamsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.common.v0.utils.Result;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传
 */
@RestController
@RequestMapping("sys/oss")
@Api(tags = "文件上传")
@Log4j2
public class SysOssController {
    @Autowired
    private SysOssService sysOssService;
    @Autowired
    private SysParamsService sysParamsService;

    private final static String KEY = Constant.CLOUD_STORAGE_CONFIG_KEY;

    @PostMapping("page")
    @ApiOperation(value = "分页")

    public Result<PageData<SysOssEntity>> page(@RequestBody SysOssBo params) {
        PageData<SysOssEntity> page = sysOssService.page(params);

        return new Result<PageData<SysOssEntity>>().ok(page);
    }

    @GetMapping("info")
    @ApiOperation(value = "云存储配置信息")

    public Result<CloudStorageConfig> info() {
        CloudStorageConfig config = sysParamsService.getValueObject(KEY, CloudStorageConfig.class);

        return new Result<CloudStorageConfig>().ok(config);
    }

    @PostMapping
    @ApiOperation(value = "保存云存储配置信息")
    @LogOperation("保存云存储配置信息")

    public Result<?> saveConfig(@RequestBody CloudStorageConfig config) {
        //校验类型
        ValidatorUtils.validateEntity(config);

        if (config.getType() == Constant.CloudService.QINIU.getValue()) {
            //校验七牛数据
            ValidatorUtils.validateEntity(config, QiniuGroup.class);
        } else if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
            //校验阿里云数据
            ValidatorUtils.validateEntity(config, AliyunGroup.class);
        } else if (config.getType() == Constant.CloudService.QCLOUD.getValue()) {
            //校验腾讯云数据
            ValidatorUtils.validateEntity(config, QcloudGroup.class);
        }

        sysParamsService.updateValueByCode(KEY, new Gson().toJson(config));

        return new Result<>();
    }

    @PostMapping("upload")
    @ApiOperation(value = "上传文件")
    //
    public Result<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return new Result<Map<String, Object>>().error(UniversalCode.UPLOAD_FILE_EMPTY);
        }

        //上传文件
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        AbstractCloudStorageService build = OSSFactory.build();
        String url = build.uploadSuffix(file.getBytes(), extension);

        //保存文件信息
        SysOssBo sysOssBo = new SysOssBo();

        CloudStorageConfig config = build.getConfig();

        sysOssBo.setType(config.getType());
        sysOssBo.setName(file.getOriginalFilename());
        sysOssBo.setUrl(url);
        sysOssBo.setCreateDate(new Date());


        Map<String, Object> data = new HashMap<>(1);
        data.put("src", sysOssService.saveOneImgObjToDb(sysOssBo));

        return new Result<Map<String, Object>>().ok(data);
    }

    /**
     * todo 后台图片待更改
     *
     * @param fileid
     * @param response
     */
    @GetMapping(value = "/showImage/{fileid}", name = "展示图片接口")
    @ApiOperation("展示图片接口")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void showImage(@ApiParam(value = "文件id", required = true) @PathVariable("fileid") String fileid,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        String cacheControl = request.getHeader("Cache-Control");
        if (StringUtil.isNotEmpty(cacheControl) && !cacheControl.startsWith("no-")) {
            // response.setContentType(MediaType.IMAGE_PNG_VALUE);
            response.addHeader(HttpHeaders.CACHE_CONTROL, "max-age=6000");
            // response.addHeader(HttpHeaders.CONTENT_LENGTH, "0");
            response.addHeader(HttpHeaders.ETAG, fileid);
            response.setStatus(HttpStatus.NOT_MODIFIED.value());
            return;
        }
        File file = sysOssService.getFileId(fileid);

        if (!file.exists()) {
            throw new ServerException("获取文件失败");
        }
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        response.addHeader(HttpHeaders.CONTENT_LOCATION, "max-age=60000");
        response.addHeader(HttpHeaders.CACHE_CONTROL, "max-age=60000");
        response.addHeader(HttpHeaders.DATE, String.valueOf(System.currentTimeMillis()));
        response.addHeader(HttpHeaders.ETAG, fileid);
        response.addHeader(HttpHeaders.EXPIRES, String.valueOf(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
        response.setStatus(HttpStatus.OK.value());
        try {
            FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
        } catch (IOException e) {
            log.error("View document exception, document id is [{}]", fileid, e);
        }
    }

    @DeleteMapping
    @ApiOperation(value = "删除")
    @LogOperation("删除")

    public Result<?> delete(@RequestBody Long[] ids) {
        sysOssService.deleteBatchIds(Arrays.asList(ids));

        return new Result<>();
    }

}
