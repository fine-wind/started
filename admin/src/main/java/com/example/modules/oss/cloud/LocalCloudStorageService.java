package com.example.modules.oss.cloud;

import com.example.common.v0.exception.ServerException;
import com.example.common.v0.exception.UniversalCode;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 本地上传
 */
public class LocalCloudStorageService extends AbstractCloudStorageService {

    public LocalCloudStorageService(com.example.modules.oss.cloud.CloudStorageConfig config) {
        this.config = config;
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        File file = new File(config.getLocalPath() + File.separator + path);
        try {
            FileUtils.copyToFile(inputStream, file);
        } catch (IOException e) {
            throw new ServerException(UniversalCode.OSS_UPLOAD_FILE_ERROR, e, "");
        }
        return config.getDomain() + "/" + path;
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getPrefix(), suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getPrefix(), suffix));
    }
}
