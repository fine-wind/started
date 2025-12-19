package com.example.started.common.v0.utils;

import lombok.extern.log4j.Log4j2;

import java.io.UnsupportedEncodingException;

/**
 * URL 转码解码
 */
@Log4j2
public class UrlUtil {
    private final static String ENCODE = "UTF-8";

    /**
     * URL 解码
     *
     * @return String
     */
    public static String getURLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            log.error(e);
        }
        return result;
    }

    /**
     * URL 转码
     */
    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            log.error(e);
        }
        return result;
    }

    /**
     * todo xing 随机url
     */
    public static String randomUrl() {
        String result = "";

        return result;
    }

}
