package com.example.started.modules.table.dto;

import com.example.started.modules.table.entity.ShowTableFieldEntity;
import lombok.Getter;


@Getter
public enum SysTableFieldTypeEnum {
    V1(1, "主键"), V2(2, "普通字段"), V3(3, "外键"), V4(4, "按钮"),
    ;
    private final Integer value;
    private final String title;

    SysTableFieldTypeEnum(int value, String title) {
        this.value = value;
        this.title = title;
    }

    /**
     * 数据库查询字段
     *
     * @param value .
     * @return .
     */
    public static boolean select(Integer value) {
        return V1.getValue().equals(value) || V2.getValue().equals(value) || V3.getValue().equals(value);
    }

    /**
     * 可以显示的字段
     *
     * @param value .
     * @return .
     */
    public static boolean show(ShowTableFieldEntity value) {
        Integer type = value.getType();
        return V2.getValue().equals(type);
    }

    /**
     * 是操作类型吗?
     *
     * @param value .
     * @return .
     */
    public static boolean op(Integer value) {
        return V4.getValue().equals(value);
    }

}