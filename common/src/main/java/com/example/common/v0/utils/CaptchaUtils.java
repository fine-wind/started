package com.example.common.v0.utils;

import com.wf.captcha.*;
import lombok.extern.log4j.Log4j2;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码
 */
@Log4j2
public class CaptchaUtils {

    public static void create(HttpServletResponse response, String uuid) throws IOException {
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        //生成验证码
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(203, 45);
        captcha.setLen(2);
        captcha.getArithmeticString();
        // 设置字体样式
        // {
        //     try {
        //         captcha.setFont(Captcha.num(10));
        //     } catch (FontFormatException e) {
        //         log.error("验证码生成失败:{}", e.getMessage());
        //     }
        // }
        try {
            captcha.out(response.getOutputStream());
            // TODO xing 验证码保存到缓存
//            SpringContextUtils.getBean(CacheService.class).setCache(CacheCommonKeys.getCaptchaKey(uuid), captcha.text(), 300L);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 校验验证码
     *
     * @param uuid uuid
     * @param code 验证码
     * @return 是否一致
     */
    public static boolean validate(String uuid, String code) {
        //获取验证码
//        String captcha = SpringContextUtils.getBean(CacheService.class).getCache(CacheCommonKeys.getCaptchaKey(uuid));

        // todo xing 验证码效验成功
        return false;// code.equalsIgnoreCase(captcha);
    }


}
