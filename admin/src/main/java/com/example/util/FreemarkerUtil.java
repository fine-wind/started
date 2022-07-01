package com.example.util;

import com.example.common.constant.Constant;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.ui.Model;

import java.io.*;
import java.util.Locale;

/**
 * freemaker 工具类
 * email xingii@outlook.com
 * datetime 2020/6/13 0:12
 *
 * @author 行星
 */
public class FreemarkerUtil {

    public static String getStr(String ftlPath, Model root) throws IOException, TemplateException {

        Configuration conf = new Configuration();
        String value = Constant.PARAM_CONF.APP_SETTINGS_CONF.TEMPLATE_PATH.getValue();
        try {
            conf.setDirectoryForTemplateLoading(new File(value));
        } catch (IOException e) {
            e.printStackTrace();
        }
        conf.setLocale(Locale.CHINA);
        conf.setDefaultEncoding("utf-8");
        conf.setClassicCompatible(true);//处理空值为空字符串


        Template temp = conf.getTemplate(ftlPath);
        Writer out = new StringWriter(2048);
        temp.process(root, out);
        return out.toString();
    }

}
