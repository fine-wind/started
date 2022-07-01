开始吧
----------

# 快速启动

一个简单的快速开发脚手架

## 打包应用和部署应用

```shell
mvn clean package
mv ./admin/target/admin.jar ./ds/app
cd ds
docker-compose up -d
```

