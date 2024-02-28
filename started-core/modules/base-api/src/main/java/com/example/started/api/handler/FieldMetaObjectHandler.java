package com.example.started.api.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.common.v0.constant.Constant;
import com.example.started.auth.client.user.SecurityUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.example.common.v0.constant.Constant.TABLE.*;

/**
 * 公共字段，自动填充值
 */
@Component
public class FieldMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {

        /* 删除状态*/
        strictInsertFill(metaObject, DEL_FLAG, String.class, Constant.SysDel.DEL_NO);

        Date now = new Date();

        //创建时间
        strictInsertFill(metaObject, CREATE_DATE, Date.class, now);
        //更新时间
        strictInsertFill(metaObject, UPDATE_DATE, Date.class, now);

        String userId = SecurityUser.getUserId();
        // todo 创建者所属部门

        //创建者
        strictInsertFill(metaObject, CREATOR, String.class, userId);
        //更新者
        strictInsertFill(metaObject, UPDATER, String.class, userId);

    }

    /**
     * 注入操作用户
     *
     * @param metaObject .
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        //更新者
        strictUpdateFill(metaObject, UPDATER, String.class, SecurityUser.getUserId());
        //更新时间
        strictUpdateFill(metaObject, UPDATE_DATE, Date.class, new Date());
    }

}
