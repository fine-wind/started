package com.example.common;

import com.example.common.exception.ServerException;
import com.example.modules.oss.service.SysOssService;
import com.example.common.utils.StringUtil;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * 文件信息表
 *
 * @since 1.0.0 2020-06-23
 */
@RestController
@RequestMapping("/files")
@Api(tags = "文件信息表")
@Log4j2
public class FilesController {
    @Autowired
    private SysOssService filesService;

    /**
     * todo 此接口鉴权 成功后301到图片地址 否则就返回其他错误
     *
     * @param fileid
     * @param request
     * @param response
     * @throws IOException
     */
    @ApiOperation("展示图片接口")
    @GetMapping(value = "/showImage/{fileid}.png", name = "展示图片接口", produces = MediaType.IMAGE_JPEG_VALUE)
    public void showImage(@ApiParam(value = "文件id", required = true) @PathVariable("fileid") String fileid,
                          HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        String cacheControl = request.getHeader("Cache-Control");
        if (StringUtil.isNotEmpty(cacheControl) && !cacheControl.startsWith("no-")) {
            // response.setContentType(MediaType.IMAGE_PNG_VALUE);
            response.addHeader(HttpHeaders.CACHE_CONTROL, "max-age=6000");
//            response.addHeader(HttpHeaders.CONTENT_LENGTH, "0");
            response.addHeader(HttpHeaders.ETAG, fileid);
            response.setStatus(HttpStatus.NOT_MODIFIED.value());
            return;
        }
        File file = filesService.getFileId(fileid);

        if (Objects.isNull(file) || !file.exists()) {
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


}
