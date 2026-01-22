开发文档
=====


开发环境
----

1. Starting ApiApplication using Java 21.0.1
2. Running with Spring Boot v3.5.6, Spring v6.2.11
3. install docker

开发步骤
---

1. 在`started-docker`文件夹里，执行`docker-compose up -d`，启动mysql、redis、nginx等
2. 在开发工具里启动项目
3. 愉快使用

## 代码结构

```text
├─api
├─auth
│  ├─auth-client
│  ├─auth-server-core
│  └─auth-server-eureka
├─cache
├─common
├─config-server
├─dynamic-datasource
├─eureka-server
├─forum
├─gateway
│  ├─JWT验证
│  ├─限流熔断
│  └─请求转发
├─generator
├─module-aggregator
├─server-demo
└─sql
```
