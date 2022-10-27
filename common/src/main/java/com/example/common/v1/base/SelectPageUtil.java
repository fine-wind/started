package com.example.common.v1.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.bo.PageBo;
import com.example.common.v0.utils.StringUtil;

import java.util.Objects;

public class SelectPageUtil {

    /**
     * 获取分页对象
     *
     * @param params 分页查询参数
     */
    protected IPage<BaseEntity> getPage(BaseDto params, PageBo pageBo) {
        return SelectPageUtil.getPage(params, pageBo, null, true);
    }

    public static <T extends BaseEntity> IPage<T> getPage(BaseDto params, PageBo pageBo, String defaultOrderField, boolean isAsc) {
        if (Objects.isNull(pageBo)) {
            pageBo = new PageBo();
        }
        //分页参数及默认值
        pageBo.setPage(pageBo.getPage() == null ? 1 : pageBo.getPage());
        pageBo.setLimit(pageBo.getLimit() == null ? Constant.PAGE.LIMIT_NUMBER : pageBo.getLimit());

        //分页对象
        Page<T> tPage = new Page<>(pageBo.getPage(), pageBo.getLimit());

        //排序字段
        String orderField = pageBo.getOrderField();
        String order = pageBo.getOrder();

        //前端字段排序
        if (StringUtil.isNotEmpty(orderField) && StringUtil.isNotEmpty(order)) {
            orderField = StringUtil.humpTo_(orderField);
            if (Constant.TABLE_ORDER.ASC.equalsIgnoreCase(order)) {
                return tPage.addOrder(OrderItem.asc(orderField));
            } else {
                return tPage.addOrder(OrderItem.desc(orderField));
            }
        }

        //没有排序字段，则不排序
        if (StringUtil.isEmpty(defaultOrderField)) {
            return tPage;
        }

        //默认排序
        if (isAsc) {
            tPage.addOrder(OrderItem.asc(defaultOrderField));
        } else {
            tPage.addOrder(OrderItem.desc(defaultOrderField));
        }

        return tPage;
    }

}
