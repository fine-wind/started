package com.example.started.dynamic.datasource.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.example.started.dynamic.datasource.TableConstant.*;

/**
 * mybatis-plus配置
 *
 * @since 1.0.0
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, CREATOR, Long.class, 123456L);
        this.strictInsertFill(metaObject, CREATE_DATE, LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, DEL_FLAG_FIELD, Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, UPDATER, Long.class, 123456L);
        this.strictUpdateFill(metaObject, UPDATE_DATE, LocalDateTime.class, LocalDateTime.now());
    }
}