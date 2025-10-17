package com.example.common.dynamic.datasource.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus配置
 *
 * @since 1.0.0
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 添加分页插件 - 这是正确的配置方式
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
        // 建议指定数据库类型
        paginationInterceptor.setDbType(DbType.MYSQL); // 根据你的数据库调整
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setMaxLimit(1000L);

        interceptor.addInnerInterceptor(paginationInterceptor);

        return interceptor;
    }

}