package com.example.common.v0.constant;

import com.example.common.v0.data.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 常量
 */
public interface Constant {
    /**
     * 一切开始的地方
     */
    Long ROOT = 0L;

    @Getter
    enum RESOURCES {
        MENU(0, "页面"),
        BUTTON(1, "按钮"),
        ROW(2, "列"),
        ;
        private final Integer value;
        private final String msg;

        RESOURCES(Integer value, String msg) {
            this.value = value;
            this.msg = msg;
        }
    }

    /**
     * 用户相关常量
     */
    interface User {
        String JOIN = "/user/join";
        String LOGIN = "/login";
        String LOGOUT = "/user/logout";
        String WEB_SOCKET = "/ws.io";

        interface Login {
            String USERNAME = "username";
            /**
             * 填写的密码
             */
            String PASSWORD = "password";
            /**
             * 验证码的 uuid
             */
            String UUID = "uuid";
            /**
             * 表单填写的验证码
             */
            String CODE = "code";
        }
    }

    /**
     * 状态
     */
    interface Status {
        /**
         * 成功 对的 是
         */
        Integer SUCCESS = 1;
        /**
         * 未知的 未确定的
         */
        Long UNKNOWN = 0L;
        /**
         * 失败 错误的 否
         */
        Integer FAIL = -1;
    }

    /**
     * 请求的常量
     */
    interface REQUEST {
        /**
         * 请求头
         */
        interface HEADER {

            String COOKIE = "Cookie";
            /**
             * token header
             */
            String TOKEN = "Authorization";
            String TOKEN_PREFIX = "Bearer ";
        }
    }

    /**
     * 通用code码
     */
    enum UniversalCode {
        UNAUTHORIZED(401, "未经授权"),
        FORBIDDEN(403, "服务器理解请求客户端的请求，但是拒绝执行此请求"),
        PRECONDITION_FAILED(412, "客户端请求信息的先决条件错误"),
        UNPROCESSABLE_ENTITY(422, "不可处理的实体"),
        SERVER_ERROR(500, "服务器内部错误"),
        ;
        private final int code;

        private final String reasonPhrase;


        UniversalCode(int code, String reasonPhrase) {
            this.code = code;
            this.reasonPhrase = reasonPhrase;
        }

        public int getCode() {
            return code;
        }

        public String getReasonPhrase() {
            return reasonPhrase;
        }
    }

    /**
     * 菜单根节点标识
     */
    Long MENU_ROOT = 0L;


    interface TABLE {
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
        String DEL_FLAG = "delFlag";

    }

    /**
     * 分页常量
     */
    interface PAGE {

        /**
         * 当前页码
         */
        String PAGE = "page";
        /**
         * 每页显示记录数
         */
        String LIMIT = "limit";
        /**
         * 默认每页多少条数据
         */
        int LIMIT_NUMBER = 10;
        /**
         * 排序字段
         */
        String ORDER_FIELD = "orderField";
        /**
         * 排序方式
         */
        String ORDER = "order";
    }


    /**
     * table 排序
     */
    interface TABLE_ORDER {
        /**
         * 升序
         */
        String ASC = "asc";
        /**
         * 降序
         */
        String DESC = "desc";
    }

    /**
     * 数据权限过滤
     */
    String SQL_FILTER = "sqlFilter";

    /**
     * 云存储配置KEY
     */
    String CLOUD_STORAGE_CONFIG_KEY = "CLOUD_STORAGE_CONFIG_KEY";
    /**
     * 邮件配置KEY
     */
    String MAIL_CONFIG_KEY = "MAIL_CONFIG_KEY";

    /**
     * 系统参数配置
     */
    interface PARAM_CONF {
        @Getter
        class KVR {
            /* 配置类型*/
            String type;
            /* 配置code*/
            final String code;
            /* 配置的实时数值*/
            @Setter
            String value;
            /* 配置的默认数值*/
            String defaultVal;
            /* 备注*/
            @Setter
            String remark;

            private KVR(String code, String value, String remark) {
                this.code = code;
                this.value = value;
                this.defaultVal = value;
                type = CONF_TYPE.SYS;
                this.remark = remark;
            }
        }

        /**
         * 参数配置类型
         */
        interface CONF_TYPE {
            /**
             * 系统配置
             */
            String SYS = "0";

            /**
             * web配置
             */
            String WEB = "1";
        }

        /**
         * 网站配置
         */
        @Getter
        enum APP_SETTINGS_CONF {

            THIS_NAME(new KVR("THIS_NAME", "网站名称", "网站名称")),

            THIS_SHORT_NAME(new KVR("THIS_SHORT_NAME", "站名", "网站名称简称")),

            THIS_HOST(new KVR("THIS_HOST", "localhost", "网站域名")),

            COPYRIGHT(new KVR("COPYRIGHT", "版权", "版权")),

            SYNOPSIS(new KVR("SYNOPSIS", "网站简介", "网站简介")),

            TEMPLATE_PATH(new KVR("TEMPLATE_PATH", "./resources/", "模板位置")),

            COMPRESS(new KVR("COMPRESS", "true", "网站压缩是否开启  true | false")),

            REGISTER(new KVR("REGISTER", "true", "是否可以注册用户  true | false")),

            CAPTCHA(new KVR("CAPTCHA", "true", "验证码是否启用  true | false")),
            CAPTCHA_FREQUENCY(new KVR("CAPTCHA_FREQUENCY", "10", "登录频率配置,达到频率时开启验证码")),

            SCRIPT(new KVR("SCRIPT", ";console.error('测试SCRIPT插入');", "输入script代码即可运行")),

            JWT_EXPIRATION(new KVR("JWT_EXPIRATION", String.valueOf(3600), "jwt过期时间(秒), 默认" + 3600 / 60 + "分钟")),
            ;

            private final KVR kvr;

            APP_SETTINGS_CONF(KVR kvr) {
                this.kvr = kvr;
            }

            public String getValue() {
                return kvr.getValue();
            }

            public static final Map<String, KVR> CONF_MAP = new LinkedHashMap<>();

            public static void init() {
                CONF_MAP.put(THIS_NAME.name(), THIS_NAME.getKvr());
                CONF_MAP.put(THIS_SHORT_NAME.name(), THIS_SHORT_NAME.getKvr());
                CONF_MAP.put(THIS_HOST.name(), THIS_HOST.getKvr());
                CONF_MAP.put(COPYRIGHT.name(), COPYRIGHT.getKvr());
                CONF_MAP.put(SYNOPSIS.name(), SYNOPSIS.getKvr());
                CONF_MAP.put(TEMPLATE_PATH.name(), TEMPLATE_PATH.getKvr());
                CONF_MAP.put(COMPRESS.name(), COMPRESS.getKvr());
                CONF_MAP.put(REGISTER.name(), REGISTER.getKvr());
                CONF_MAP.put(CAPTCHA.name(), CAPTCHA.getKvr());
                CONF_MAP.put(SCRIPT.name(), SCRIPT.getKvr());
                CONF_MAP.put(JWT_EXPIRATION.name(), JWT_EXPIRATION.getKvr());
            }
        }
    }

    /**
     * 定时任务状态
     */
    enum ScheduleStatus {
        /**
         * 暂停
         */
        PAUSE(0),
        /**
         * 正常
         */
        NORMAL(1);

        private final int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    @Getter
    enum UserConfigItemEnum {
        SUPER_USER(0),
        ;
        private final Integer value;

        UserConfigItemEnum(Integer value) {
            this.value = value;
        }
    }

    /**
     * 云服务商
     */
    enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3),
        /**
         * FASTDFS
         */
        FASTDFS(4),
        /**
         * 本地
         */
        LOCAL(5),
        /**
         * MinIO
         */
        MINIO(6);

        private final int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 短信服务商
     */
    enum SmsService {
        /**
         * 阿里云
         */
        ALIYUN(1),
        /**
         * 腾讯云
         */
        QCLOUD(2),
        /**
         * 七牛
         */
        QINIU(3);

        private final int value;

        SmsService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 系统常量
     */
    interface SysDel {
        /**
         * 已删除
         */
        String DEL_YES = "1";
        /**
         * 未删除
         */
        String DEL_NO = "0";

    }
}
