package com.example.common.v0.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * cookie 工具类
 * <p>
 * email xingii@outlook.com
 * datetime 2020-8-27 14:50
 *
 * @author 行星
 */
@Log4j2
public class CookieUtils {

    public static final int COOKIE_HALF_HOUR = 30 * 60;

    /**
     * 根据Cookie名称得到Cookie对象，不存在该对象则返回Null
     *
     * @param request 请求体
     * @param name    cookie name
     * @return .
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        Cookie cookie = null;
        for (Cookie c : cookies) {
            if (name.equals(c.getName())) {
                cookie = c;
                break;
            }
        }
        return cookie;
    }

    /**
     * 根据Cookie名称直接得到Cookie值
     *
     * @param request 请求体
     * @param name    .
     * @return .
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    /**
     * 移除cookie
     *
     * @param request  请求体
     * @param response .
     * @param name     这个是名称，不是值
     */
    public static void removeCookie(HttpServletRequest request,
                                    HttpServletResponse response, String name) {
        if (null == name) {
            return;
        }
        Cookie cookie = getCookie(request, name);
        if (null != cookie) {
            cookie.setPath("/");
            cookie.setValue("");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    /**
     * 添加一条新的Cookie，可以指定过期时间(单位：秒)
     *
     * @param response 返回体
     * @param name     cookie key
     * @param value    cookie值
     * @param maxValue 多少秒
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxValue) {
        if (StringUtil.isEmpty(name)) {
            return;
        }
        if (null == value) {
            value = "";
        }
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxValue != 0) {
            cookie.setMaxAge(maxValue);
        } else {
            cookie.setMaxAge(COOKIE_HALF_HOUR);
        }
        response.addCookie(cookie);
        try {
            response.flushBuffer();
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * 添加一条新的Cookie，默认30分钟过期时间
     *
     * @param response .
     * @param name     .
     * @param value    .
     */
    public static void addCookie(HttpServletResponse response, String name, String value) {
        addCookie(response, name, value, COOKIE_HALF_HOUR);
    }

    /**
     * 将cookie封装到Map里面
     *
     * @param request .
     * @return .
     */
    public static Map<String, Cookie> getCookieMap(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            Map<String, Cookie> cookieMap = new HashMap<>();
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
            return cookieMap;
        }
        return new HashMap<>(0);
    }

}
