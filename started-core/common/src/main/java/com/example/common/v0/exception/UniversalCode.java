package com.example.common.v0.exception;

import com.example.common.v0.constant.Constant;

/**
 * 错误编码，由5位数字组成，前2位为模块编码，后3位为业务编码
 * 如：10001（10代表系统模块，001代表业务代码）
 *
 * @see Constant.UniversalCode
 * @since 1.0.0
 */
@Deprecated
public interface UniversalCode {
    int INTERNAL_SERVER_ERROR = 500;
    int NOT_NULL = 10001;
    int DB_RECORD_EXISTS = 10002;
    int PARAMS_GET_ERROR = 10003;
    int PASSWORD_ERROR = 10009;
    int ACCOUNT_NOT_EXIST = 10010;
    int SUPERIOR_DEPT_ERROR = 10011;
    int SUPERIOR_MENU_ERROR = 10012;
    int DATA_SCOPE_PARAMS_ERROR = 10013;

    int UPLOAD_FILE_EMPTY = 10019;
    int OSS_UPLOAD_FILE_ERROR = 10024;
    int MAIL_TEMPLATE_NOT_EXISTS = 10026;
    int JOB_ERROR = 10028;
    int INVALID_SYMBOL = 10029;
    int JSON_FORMAT_ERROR = 10030;
    int SMS_CONFIG = 10031;
    int SUPERIOR_REGION_ERROR = 10038;
}
