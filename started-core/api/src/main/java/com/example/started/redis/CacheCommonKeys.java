package com.example.started.redis;

/**
 * @since 1.0.0
 */
public class CacheCommonKeys {

    public static final String LANGUAGE = "sys:language";

    /**
     * 系统参数Key
     */
    public static String getSysParamsKey(String key) {
        return "sys:params:" + key;
    }

    /**
     * 验证码Key
     */
    public static String getUserLoginCs(String username) {
        return "sys:userLcs:" + username;
    }

    /**
     * 验证码Key
     */
    public static String getCaptchaKey(String uuid) {
        return "sys:captcha:" + uuid;
    }

    /**
     * 登录用户Key
     * todo 使用 Set 形式记录每个人有多少客户端
     *
     * @param username username
     * @return k
     */
    public static String getSecurityUserToken(String username) {
        return "sys:security:token:" + username;
    }

    /**
     * 登录用户信息缓存 key
     *
     * @param username username
     * @return key
     */
    public static String getSecurityUserKey(String username) {
        return "sys:security:user:" + username;
    }

    /**
     * 登录用户信息缓存 key
     *
     * @param username username
     * @return key
     */
    public static String getSecurityRoleKey(String username) {
        return "sys:security:role:" + username;
    }

    /**
     * 系统日志Key
     */
    public static String getSysLogKey() {
        return "sys:log";
    }

    /**
     * 系统资源Key
     */
    public static String getSysResourceKey() {
        return "sys:resource";
    }

    /**
     * 用户菜单导航Key
     */
    public static String getUserMenuNavKey(Long userId) {
        return "sys:user:nav:" + userId;
    }

    /**
     * 用户权限标识Key
     */
    public static String getUserPermissionsKey(Long userId) {
        return "sys:user:permissions:" + userId;
    }
}
