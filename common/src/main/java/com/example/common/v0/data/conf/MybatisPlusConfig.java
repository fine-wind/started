package com.example.common.v0.data.conf;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.example.common.v0.data.interceptor.DataFilterInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * mybatis-plus配置
 *
 * @since 1.0.0
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 配置数据权限
     */
    @Bean
    @Order(1)
    public DataFilterInterceptor dataFilterInterceptor() {
        return new DataFilterInterceptor();
    }

    /**
     * todo 自定义动态表名
     *
     * @return 配置
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();

//        dynamicTableNameInnerInterceptor.setTableNameHandler((sql, tableName) -> {
//            // 获取参数方法
//            Map<String, Object> paramMap = RequestDataHelper.getRequestData();
//            paramMap.forEach((k, v) -> System.err.println(k + "----" + v));
//
//            String year = "_2018";
//            int random = new Random().nextInt(10);
//            if (random % 2 == 1) {
//                year = "_2019";
//            }
//            return tableName + year;
//        });
//        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);

        return interceptor;
    }

}
