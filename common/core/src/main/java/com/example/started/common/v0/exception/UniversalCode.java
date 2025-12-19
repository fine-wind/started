package com.example.started.common.v0.exception;

import com.example.started.common.constant.Constant;

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
    int PARAMS_GET_ERROR = 10003;
    int SUPERIOR_MENU_ERROR = 10012;

    int UPLOAD_FILE_EMPTY = 10019;
    int MAIL_TEMPLATE_NOT_EXISTS = 10026;
    int JOB_ERROR = 10028;
    int INVALID_SYMBOL = 10029;
    int SMS_CONFIG = 10031;
    int SUPERIOR_REGION_ERROR = 10038;
}
