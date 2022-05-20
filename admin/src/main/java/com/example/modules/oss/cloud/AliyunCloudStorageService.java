package com.example.modules.oss.cloud;

import com.aliyun.oss.OSSClient;
import com.example.common.exception.ServerException;
import com.example.common.exception.UniversalCode;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 阿里云存储
 */
public class AliyunCloudStorageService extends AbstractCloudStorageService {

    public AliyunCloudStorageService(CloudStorageConfig config) {
        this.config = config;
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        OSSClient client = new OSSClient(config.getEndPoint(), config.getAccessKey(),
                config.getSecretKey());
        try {
            client.putObject(config.getBucketName(), path, inputStream);
            client.shutdown();
        } catch (Exception e) {
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
