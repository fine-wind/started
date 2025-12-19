package com.example.started.common.v0.utils;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.Random;

/**
 * 数字工具类
 */
public class NumberUtils {

    private static final String[] num = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static final String[] unit = {"圆", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟"};

    /**
     * 将数字转换为大写的
     *
     * @param str 数字的字符串格式
     * @return 大写的数字
     */
    public static String intToBig(@PathVariable(name = "src") String str) {
        if (StringUtil.isEmpty(str) || "0".equals(str)) {
            return ("零圆");
        }

        String[] strs = str.split("\\.");
        int anInt = Integer.parseInt(strs[0]);
        StringBuffer dst = new StringBuffer(0);
        /*整数部分*/
        int count = 0;
        while (anInt > 0) {
            dst = new StringBuffer(num[anInt % 10] + unit[count]).append(dst);
            anInt = anInt / 10;
            count++;
        }
        dst = new StringBuffer(dst.toString().replaceAll("零[仟佰拾]", "零").replaceAll("零+万", "万")
                .replaceAll("零+亿", "亿").replaceAll("亿万", "亿零")
                .replaceAll("零+", "零").replaceAll("零圆", "圆"));
        /*小数部分*/
        if (strs.length > 1 && !StringUtil.isEmpty(strs[1]) && StringUtil.isNumeric(strs[1])) {
            dst.append("点");
            for (char c : strs[1].toCharArray()) {
                int decimal = Integer.parseInt(String.valueOf(c));
                dst.append(num[decimal]);
            }
        }
        return (StringUtil.isEmpty(dst.toString()) ? new StringBuffer("零") : dst).append(unit[0]).toString().replaceAll("圆点", "点").replaceAll("圆+", "圆");
    }

    /**
     * 返回一个随机数
     *
     * @return 随机数
     */
    public static int getRandom() {
        Random jjj = new Random();

        return jjj.nextInt(100);
    }

    /**
     * 返回一个随机数
     *
     * @param i 长度
     * @return 随机数
     */
    public static String getRandom(int i) {
        Random jjj = new Random();
        if (i <= 0)
            return "";
        StringBuilder jj = new StringBuilder();
        for (int k = 0; k < i; k++) {
            jj.append(jjj.nextInt(9));
        }
        return jj.toString();
    }

}
