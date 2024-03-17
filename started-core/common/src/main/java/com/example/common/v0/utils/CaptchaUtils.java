package com.example.common.v0.utils;

import com.example.common.v3.cache.CacheKeysTime;
import com.example.common.v3.cache.RedisUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import static com.example.common.v0.constant.Constant.PARAM_CONF.APP_SETTINGS_CONF.CAPTCHA;

/**
 * 验证码
 */
public class CaptchaUtils {
    static Random random = new Random();

    /**
     * @param uuid  验证码标识
     * @return 验证码base64
     * @throws IOException io异常
     */
    public static String create(String uuid) throws IOException {
        Integer width = 100, height = 100;
        // 步骤一 绘制一张内存中图片
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 步骤二 图片绘制背景颜色 ---通过绘图对象
        Graphics graphics = bufferedImage.getGraphics();// 得到画图对象 --- 画笔
        // 绘制任何图形之前 都必须指定一个颜色
        graphics.setColor(getRandColor(200, 250));
        graphics.fillRect(0, 0, width, height);

        // 步骤三 绘制边框
        graphics.setColor(Color.WHITE);
        graphics.drawRect(0, 0, width - 1, height - 1);

        // 步骤四 四个随机数字
        Graphics2D graphics2d = (Graphics2D) graphics;
        // 设置输出字体
        graphics2d.setFont(new Font("宋体", Font.BOLD, 18));

        // 定义x坐标
        int x = 5;
        String word = String.valueOf(random.nextInt(9999));;
        for (int i = 0; i < word.length(); i++) {
            // 随机颜色
            graphics2d.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            // 旋转 -30 --- 30度
            int jiaoDu = random.nextInt(60) - 30;
            // 换算弧度
            double theta = jiaoDu * Math.PI / 180;
            // 获得字母数字
            char c = word.charAt(i);
            // 将c 输出到图片
            graphics2d.rotate(theta, x, 20);
            graphics2d.drawString(String.valueOf(c), x, 20);
            graphics2d.rotate(-theta, x, 20);
            x += 20;
        }

        // 步骤五 绘制干扰线
        graphics.setColor(getRandColor(160, 200));
        int x1, x2, y1, y2;
        for (int i = 0; i < 30; i++) {
            x1 = random.nextInt(width);
            x2 = random.nextInt(12);
            y1 = random.nextInt(height);
            y2 = random.nextInt(12);
            graphics.drawLine(x1, y1, x1 + x2, x2 + y2);
        }

        graphics.dispose();// 释放资源
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", stream);
        SpringContextUtils.getBean(RedisUtils.class).setCache(CacheKeysTime.SYS_CAPTCHA + uuid, word);
        return "data:image/png;base64," + new String(Base64.getEncoder().encode(stream.toByteArray()));
    }

    /**
     * todo 校验验证码
     *
     * @param uuid uuid
     * @param code 验证码
     * @return 是否一致
     */
    public static boolean validate(String uuid, String code) {
        if (Boolean.parseBoolean(CAPTCHA.getValue())) {
            String captcha = SpringContextUtils.getBean(RedisUtils.class).getCache(CacheKeysTime.SYS_CAPTCHA + uuid);
            return code.equalsIgnoreCase(captcha);
        }
        //获取验证码
        return true;
    }


    /**
     * 取其某一范围的color
     *
     * @param fc int 范围参数1
     * @param bc int 范围参数2
     * @return Color
     */
    private static Color getRandColor(int fc, int bc) {
        // 取其随机颜色
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
