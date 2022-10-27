package com.example.modules.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.common.v0.constant.Constant;
import com.example.modules.security.user.SecurityUser;
import com.example.modules.security.user.SecurityUserDetails;
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

        SecurityUserDetails userInfo = SecurityUser.getUser();

        /* 删除状态*/
        strictInsertFill(metaObject, DEL_FLAG, String.class, Constant.SysDel.DEL_NO);

        if (userInfo == null) {
            return;
        }
        Date date = new Date();

        //创建者
        strictInsertFill(metaObject, CREATOR, String.class, userInfo.getId());
        //创建时间
        strictInsertFill(metaObject, CREATE_DATE, Date.class, date);

        // TODO 创建者所属部门

        //更新者
        strictInsertFill(metaObject, UPDATER, String.class, userInfo.getId());
        //更新者
        strictInsertFill(metaObject, UPDATER, String.class, userInfo.getId());
        //更新时间
        strictInsertFill(metaObject, UPDATE_DATE, Date.class, date);

    }

    /**
     * 注入操作用户
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        if (true) {
            return;
        }
        //更新者
//        strictUpdateFill(metaObject, UPDATER, Long.class, userinfo.getId());
        //更新时间
//        strictUpdateFill(metaObject, UPDATE_DATE_TABLE, Date.class, new Date());
    }
//

}
