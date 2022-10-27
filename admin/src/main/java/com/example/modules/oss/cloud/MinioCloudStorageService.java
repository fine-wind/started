package com.example.modules.oss.cloud;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import com.example.common.v0.exception.UniversalCode;
import com.example.common.v0.exception.ServerException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * MinIO 存储
 */
public class MinioCloudStorageService extends AbstractCloudStorageService {
    private MinioClient minioClient;

    public MinioCloudStorageService(CloudStorageConfig config) {
        this.config = config;
        //初始化
        init();
    }

    private void init() {
        try {
            minioClient = new MinioClient(config.getEndPoint(), config.getAccessKey(), config.getSecretKey());
        } catch (InvalidEndpointException | InvalidPortException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            //如果BucketName不存在，则创建
            boolean found = minioClient.bucketExists(config.getBucketName());
            if (!found) {
                minioClient.makeBucket(config.getBucketName());
            }

            minioClient.putObject(config.getBucketName(), path, inputStream, Long.valueOf(inputStream.available()),
                    null, null, "application/octet-stream");
        } catch (Exception e) {
            throw new ServerException(UniversalCode.OSS_UPLOAD_FILE_ERROR, e, "");
        }

        return config.getEndPoint() + "/" + config.getBucketName() + "/" + path;
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
