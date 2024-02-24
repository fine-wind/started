package com.example.common.v0.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 */

@Log4j2
public class CommonUtils {

    private static final Logger LOG = LoggerFactory.getLogger(CommonUtils.class);


    /**
     * 把obj对象转换为map
     *
     * @param obj 对象
     * @return 对象转成的map
     */
    public static Map<String, Object> convertObjToMap(Object obj) {
        Map<String, Object> reMap = new HashMap<>();
        if (obj == null) {
            return null;
        }

        // 获取所有字段,public和protected和private,但是包括父类字段
        Field f;
        Field[] fields;
        for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                try {
                    f = clazz.getDeclaredField(field.getName());
                    f.setAccessible(true);
                    Object o = f.get(obj);
                    if (o == null || "".equals(o)) {// 值为空，直接跳过
                        continue;
                    }
                    reMap.put(field.getName(), o);
                } catch (NoSuchFieldException e) {
                    LOG.error("NoSuchFieldException", e);
                } catch (IllegalArgumentException e) {
                    LOG.error("IllegalArgumentException", e);
                } catch (IllegalAccessException e) {
                    LOG.error("IllegalAccessException", e);
                }
            }
        }
        return reMap;
    }

    /**
     * 将Object对象，根据key升序排列
     *
     * @param object 对象
     * @return key1=value1&key2=value2&key3=value3
     */
    public static String orderByKey(Object object) {
        Map<String, Object> map = CommonUtils.convertObjToMap(object);
        List<Map.Entry<String, Object>> listMap = new ArrayList<>(map.entrySet());
        // key升序排序
        listMap.sort(Comparator.comparing(Map.Entry::getKey));

        StringBuilder value = new StringBuilder(100);
        for (Map.Entry<String, Object> entry : listMap) {
            value.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        if (!value.isEmpty()) {
            return value.substring(0, value.length() - 1);
        }
        return null;
    }


    /**
     * combineSydwCore
     * 该方法是用于相同对象不同属性值的合并，如果两个相同对象中同一属性都有值，那么sourceBean中的值会覆盖tagetBean重点的值
     *
     * @param sourceBean 被提取的对象bean
     * @param targetBean 用于合并的对象bean
     * @return 合并后的对象bean
     * @date 2017年12月26日 下午1:53:19
     */
    public static Object combineObject(Object sourceBean, Object targetBean) {

        Class<?> sourceBeanClass = sourceBean.getClass();
        Class<?> targetBeanClass = targetBean.getClass();

        Field[] sourceFields = sourceBeanClass.getDeclaredFields();
        Field[] targetFields = sourceBeanClass.getDeclaredFields();
        for (int i = 0; i < sourceFields.length; i++) {
            Field sourceField = sourceFields[i];
            Field targetField = targetFields[i];
            sourceField.setAccessible(true);
            targetField.setAccessible(true);
            try {
                if (!(sourceField.get(sourceBean) == null) && !"serialVersionUID".equals(sourceField.getName())) {
                    targetField.set(targetBean, sourceField.get(sourceBean));
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                log.error(e);
            }
        }
        return targetBean;
    }

    /**
     * @param source 被复制的实体类对象
     * @param to     复制完后的实体类对象
     */
    public static void copy(Object source, Object to) {
        try {
            // 获取属性
            BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(), Object.class);
            PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();

            BeanInfo destBean = Introspector.getBeanInfo(to.getClass(), Object.class);
            PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : sourceProperty) {
                for (PropertyDescriptor propertyDescriptor : destProperty) {
                    if (descriptor.getName().equals(propertyDescriptor.getName())) {
                        // 调用source的getter方法和dest的setter方法
                        propertyDescriptor.getWriteMethod().invoke(to, descriptor.getReadMethod().invoke(source));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("property copy error", e);
        }
    }

    /**
     * 浅度复制一个对象
     *
     * @param o .
     */
    public static <T> T copy(Object o, Class<T> clazz) {
        if (o == null) {
            return null;
        }
        String modelJsonStr = JSONObject.toJSONString(o);
        return JSONObject.parseObject(modelJsonStr, clazz);
    }


    /**
     * 判断变量是否加密
     *
     * @param str 变量
     * @return true:已加密    false:未加密
     */
    public static boolean isEncrypt(String str) {
        return str != null && str.trim().length() > 15 && !"0".equals(str) && !"-1".equals(str) && !"1".equals(str);
    }

    /**
     * 所有任务是否执行完毕
     *
     * @param taskList 任务集合
     */
    public static void allTaskDone(List<Future<?>> taskList) {
        boolean isAllDone;
        do {
            isAllDone = true;
            for (Future<?> future : taskList) {
                if (!future.isDone()) {
                    isAllDone = false;
                    break;
                }
            }
            if (!isAllDone) {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    log.error(e);
                }
            }
        } while (!isAllDone);
    }

    /**
     * 去掉html标签
     *
     * @param htmlStr .
     * @return .
     */
    public static String delHTMLTag(String htmlStr) {
        //定义script的正则表达式
        String regEx_script = "<script[^>]*?>[\\s\\S]*?</script>";
        //定义style的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?</style>";
        //定义HTML标签的正则表达式
        String regEx_html = "<[^>]+>";

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        //过滤script标签
        htmlStr = m_script.replaceAll("");

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        //过滤style标签
        htmlStr = m_style.replaceAll("");

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        //过滤html标签
        htmlStr = m_html.replaceAll("");
        //返回文本字符串
        return htmlStr.trim();
    }

    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

    /**
     * 替换成原图
     *
     * @param pic 其他图片地址
     * @return 原图图片地址
     */
    public static String replacePicOriginal(String pic) {
        pic = pic.replaceAll("/preview/", "/original/")
                .replaceAll("/middle/", "/original/");

        return pic;
    }

    /**
     * 替换成中图
     *
     * @param pic 其他图片地址
     * @return 中图图片地址
     */
    public static String replacePicMiddle(String pic) {
        pic = pic == null ? "" : pic;
        pic = pic.replaceAll("/preview/", "/middle/")
                .replaceAll("/original/", "/middle/");

        return pic;
    }

    /**
     * 替换成小图
     *
     * @param pic 其他图片地址
     * @return 小图图片地址
     */
    public static String replacePicPreview(String pic) {
        pic = pic.replaceAll("/middle/", "/preview/")
                .replaceAll("/original/", "/preview/");
        return pic;
    }

    /**
     * Map转成实体对象
     *
     * @param map   map实体对象包含属性
     * @param clazz 实体对象类型
     * @return 实体对象
     */
    public static Object mapToObject(Map<String, Object> map, Class clazz) {
        if (map == null) {
            return null;
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
        } catch (Exception e) {
            log.error(e);
        }
        return obj;
    }

    /**
     * 通过json 将map转为实体
     *
     * @param map   map
     * @param clazz Class
     * @param <T>   class
     * @return class
     */
    public static <T> T mapToObjectByJson(Map<Object, Object> map, Class<T> clazz) {
        if (map == null) {
            return null;
        }
        try {
            return JSON.parseObject(JSON.toJSONString(map), clazz);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * 去除html代码中含有的标签
     *
     * @param htmlStr html字符串
     * @return 去掉标签的字符串
     */
    public static String delHtmlTags(String htmlStr) {
        //定义script的正则表达式，去除js可以防止注入
        String scriptRegex = "<script[^>]*?>[\\s\\S]*?</script>";
        //定义style的正则表达式，去除style样式，防止css代码过多时只截取到css样式代码
        String styleRegex = "<style[^>]*?>[\\s\\S]*?</style>";
        //定义HTML标签的正则表达式，去除标签，只提取文字内容
        String htmlRegex = "<[^>]+>";
        //定义空格,回车,换行符,制表符
        String spaceRegex = "\\s*|\t|\r|\n";

        // 过滤script标签
        htmlStr = htmlStr.replaceAll(scriptRegex, "");
        // 过滤style标签
        htmlStr = htmlStr.replaceAll(styleRegex, "");
        // 过滤html标签
        htmlStr = htmlStr.replaceAll(htmlRegex, "");
        // 过滤空格等
        htmlStr = htmlStr.replaceAll(spaceRegex, "");
        return htmlStr.trim(); // 返回文本字符串
    }


    /**
     * 根据日期和随机码生成订单号
     *
     * @return 生成订单号
     */
    public static String getOrderNumber() {
        String num = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
        return num + (int) (Math.random() * 90 + 10);
    }


}
