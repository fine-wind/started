开始吧
----------

# 快速启动

## 打包应用和部署应用

```shell
mvn clean package
mv ./admin/target/admin.jar ./ds/app
cd ds
docker-compose up -d
```

