package com.example.generator.config;

import com.example.generator.modules.dao.MySQLGeneratorDao;
import com.example.generator.modules.dao.PostgreSQLGeneratorDao;
import com.example.generator.modules.dao.SQLServerGeneratorDao;
import com.example.generator.modules.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 数据库配置
 */
//@Configuration
public class DbConfig {
    @Value("${spring.databaseType: mysql}")
    private String database;
    @Autowired(required = false)
    private MySQLGeneratorDao mySQLGeneratorDao;
    @Autowired
    private OracleGeneratorDao oracleGeneratorDao;
    @Autowired
    private SQLServerGeneratorDao sqlServerGeneratorDao;
    @Autowired
    private PostgreSQLGeneratorDao postgreSQLGeneratorDao;

    @Bean
    @Primary
    public GeneratorDao getGeneratorDao() {
        if ("mysql".equalsIgnoreCase(database)) {
            return mySQLGeneratorDao;
        } else if ("oracle".equalsIgnoreCase(database)) {
            return oracleGeneratorDao;
        } else if ("sqlserver".equalsIgnoreCase(database)) {
            return sqlServerGeneratorDao;
        } else if ("postgresql".equalsIgnoreCase(database)) {
            return postgreSQLGeneratorDao;
        } else {
            throw new RuntimeException("不支持当前数据库：" + database);
        }
    }
}
