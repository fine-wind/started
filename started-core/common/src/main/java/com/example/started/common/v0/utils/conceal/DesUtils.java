package com.example.started.common.v0.utils.conceal;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * des 加密
 *
 * @author 行星
 * @date 2020/1/17 16:15
 */
public class DesUtils {

    /**
     * 加密
     *
     * @param data 要加密的数据
     * @param key  密钥
     */
    public static String encrypt(String data, String key) {
        try {
            // 使用DES 加密，key和iv都使用pwd
            // 根据pwd，生成DES加密后的密钥，SecretKeySpec对象
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "DES");
            // 根据pwd，创建一个初始化向量IvParameterSpec对象
            IvParameterSpec iv = new IvParameterSpec(key.getBytes());
            // 创建密码器，参数：算法/模式/填充
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            // 用key和iv初始化密码器，参1：opmode，操作模式-加密、解密等。
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            // 执行（加密）并返回结果（字节数组）
            byte[] resultBytes = cipher.doFinal(data.getBytes("GB2312"));
            // 转换成十六进制（大写）
            return bytes2Hex(resultBytes).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param data 要解密的数据
     * @param key  密钥
     */
    public static String decrypt(String data, String key) {
        try {
            // 把加密的十六进制字符串数据转换成字节数组
            if ("undefined".equals(data) || data == null) {
                return null;
            }
            int len = data.length() >> 1;
            byte[] dataBytes = new byte[len];
            for (int i = 0; i < len; i++) {
                int index = i << 1;
                dataBytes[i] = (byte) Integer.parseInt(data.substring(index, index + 2), 16);
            }

            // 创建key和iv
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "DES");
            IvParameterSpec iv = new IvParameterSpec(key.getBytes());

            // DESUtils 解密
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] resultBytes = cipher.doFinal(dataBytes);

            return new String(resultBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字节数组转换成十六进制字符串
     */
    private static String bytes2Hex(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() < 2) {
                result.append("0");
            }
            result.append(hex);
        }
        return result.toString();
    }

}
