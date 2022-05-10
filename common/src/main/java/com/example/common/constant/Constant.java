package com.example.common.constant;

import com.example.common.data.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量
 */
public interface Constant {
    /**
     * 一切开始的地方
     */
    Long ROOT = 0L;

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

        public Integer getValue() {
            return value;
        }

        public String getMsg() {
            return msg;
        }
    }

    /**
     * 用户相关常量
     */
    interface User {
        String JOIN = "/api/user/join";
        String WEB_SOCKET = "/ws.io";
        String LOGIN = "/api/user/logIn";
        String LOGOUT = "/api/user/logout";

        interface Login {
            String USERNAME = "username";
            /**
             * 填写的密码
             */
            String PASSWORD = "password";
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
            String TOKEN = "token";
        }
    }

    /**
     * 通用code码
     */
    enum UniversalCode {
        UNAUTHORIZED(401, "未经授权"),
        FORBIDDEN(403, "服务器理解请求客户端的请求，但是拒绝执行此请求"),
        UNPROCESSABLE_ENTITY(422, "不可处理的实体"),
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
        String UPDATE_DATE_TABLE = "update_date";

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
     * 参数配置
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
         * 登录验证码配置
         */
        interface LOGIN_CAPTCHA {

            /**
             * 登录验证码
             */
            String LOGIN_CAPTCHA = "LOGIN_CAPTCHA";
            /**
             * 配置备注
             */
            String REMARK = "登录验证码配置";
            /**
             * 在一分钟内的错误频率 10次
             * 达到该次数后启用验证码
             */
            int LOGIN_CAPTCHA_ERROR_FREQUENCY = 10;
        }

        /**
         * 网站配置
         */
        interface APP_SETTINGS_CONF {
            KVR THIS_NAME = new KVR("NAME", "网站名称", "网站名称");
            KVR THIS_HOST = new KVR("thisHost", "localhost", "网站域名");

            KVR SYNOPSIS = new KVR("SYNOPSIS", "网站简介", "网站简介");
            KVR TEMPLATE_PATH = new KVR("TEMPLATE_PATH", "./resources/", "模板位置");
            KVR COMPRESS = new KVR("COMPRESS", "true", "网站压缩，压缩需键入 true | false");
            KVR PAGE_CACHE_PATH = new KVR("PAGE_CACHE_PATH", "", "todo 页面缓存路径");
            KVR SCRIPT = new KVR("SCRIPT", "console.log('test script')", "输入script代码即可运行");
            KVR STATIC_VERSION = new KVR("STATIC_VERSION", "0.0.1", "js,css的版本号，用于清理浏览器端缓存");
            KVR BEIAN_IPC = new KVR("BEIAN_IPC", "", "网站备案号");
            KVR BEIAN_MIIT = new KVR("BEIAN_MIIT", "https://beian.miit.gov.cn/", "公信部连接");

            KVR JWT_SECRETKEY = new KVR("JWT_SECRETKEY", "JWT_SECRETKEY", "用户登录token加密密钥");
            KVR JWT_EXPIRATION = new KVR("JWT_EXPIRATION", String.valueOf(3600), "jwt过期时间(秒), 默认" + 3600 / 60 + "分钟");

            Map<String, KVR> CONF_MAP = new HashMap<>(12);

            static void init() {
                CONF_MAP.put(THIS_NAME.getCode(), THIS_NAME);
                CONF_MAP.put(THIS_HOST.getCode(), THIS_HOST);
                CONF_MAP.put(SYNOPSIS.getCode(), SYNOPSIS);
                CONF_MAP.put(TEMPLATE_PATH.getCode(), TEMPLATE_PATH);
                CONF_MAP.put(COMPRESS.getCode(), COMPRESS);
                CONF_MAP.put(PAGE_CACHE_PATH.getCode(), PAGE_CACHE_PATH);
                CONF_MAP.put(SCRIPT.getCode(), SCRIPT);
                CONF_MAP.put(STATIC_VERSION.getCode(), STATIC_VERSION);
                CONF_MAP.put(BEIAN_IPC.getCode(), BEIAN_IPC);
                CONF_MAP.put(BEIAN_MIIT.getCode(), BEIAN_MIIT);
                CONF_MAP.put(JWT_SECRETKEY.getCode(), JWT_SECRETKEY);
                CONF_MAP.put(JWT_EXPIRATION.getCode(), JWT_EXPIRATION);
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
