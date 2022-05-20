package com.example.util;

import com.example.common.constant.Constant;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.ui.Model;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
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


    /*
     * 打印html到文件
     *
     * @param ftlPath  模板的相对位置
     * @param model    生成文件要用到的数据
     * @param htmlPath 生成文件到何处
     * @throws Exception 中间产生的异常
     */
    /*public static void printHtml(String ftlPath, Model model, String htmlPath) throws Exception {
        AppSettings appSettings = AppSettings.getInstance();

        //1.创建配置类,这个 configuration 是 freemarker 提供的,不要导错包了
        Configuration configuration = new Configuration(Configuration.getVersion());

        //2.设置模板所在的目录,这里定义的就是刚刚test.ftl所存放的真实目录
        //注意这里是文件夹路径,不是文件路径
        File file = new File(appSettings.getTemplatePath());
        configuration.setDirectoryForTemplateLoading(file);
        //3.设置字符集
        configuration.setDefaultEncoding("utf-8");
        //4.加载模板
        Template template = configuration.getTemplate(ftlPath);
        //5.创建数据模型,就是用来填充模板那些插值的,可以用map,也可以定义对象,一般都是map,注意的是key需要跟插值中的对应上
        *//*`
     * @see model 用了这个数据
     *//*
        //6.创建 Writer 对象,代表生成的源代码会存放到哪里
        Writer out = new FileWriter(appSettings.getWebapp() + htmlPath);
        //7.输出
        template.process(model, out);
        //8.关闭 Writer 对象,切记不要忘记关流,不然以上的数据都还是在内存中,需要refresh才可以持久化到磁盘,这是IO流的知识点.
        out.close();
    }*/
}
