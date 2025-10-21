package com.example.started.dynamic.datasource;

public interface TableConstant {

    /**
     * @see BaseEntity#getCreator()
     */
    String CREATOR = "creator";

    /**
     * @see BaseEntity#getCreateDate()
     */
    String CREATE_DATE = "createDate";

    /**
     * 创建时间字段名
     */
    String CREATE_DATE_TABLE = "create_date";

    /**
     * @see BaseEntity#getUpdater()
     */
    String UPDATER = "updater";
    /**
     * @see BaseEntity#getUpdateDate()
     */
    String UPDATE_DATE = "updateDate";
    // String UPDATE_DATE_TABLE = "update_date";

    /**
     * @see BaseEntity#getDelFlag()
     */
    String DEL_FLAG_FIELD = "delFlag";

    interface DEL_FLAG {
        String SHOW = "0";
        String DEL = "1";
    }
}
