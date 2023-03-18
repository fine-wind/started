部署应用
=======

```shell
mvn clean package
mv ./admin/target/admin.jar ./ds/app
cd ds
docker-compose up -d
```