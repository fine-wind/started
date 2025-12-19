package com.example.started.common.v0.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 */
public class HtmlUtils {

    /**
     * 压缩html
     *
     * @param htmlStr .
     * @return zip htmlStr
     */
    public static String zip(String htmlStr) {

        // 删除//abc... 行
        htmlStr = htmlStr.replaceAll("\\s+//.*", "");
        /* 删除注释 */
        htmlStr = htmlStr.replaceAll("<!--.*?-->", "");
        // 删除 /*abc*/
        htmlStr = htmlStr.replaceAll("/\\*.*?\\*/", "");
        // 去除空白
        htmlStr = htmlStr.replaceAll("\\s{2,}", "");
        /* 两个标签之间的空白字符*/
        htmlStr = htmlStr.replaceAll(">\\s<", "<");
        return htmlStr.trim();
    }


    /**
     * 去掉html标签
     *
     * @param htmlStr .
     * @return .
     */
    public static String delHTMLTag(String htmlStr) {
        //定义script的正则表达式
        String scriptRegex = "<script[^>]*?>[\\s\\S]*?</script>";
        //定义style的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?</style>";
        //定义HTML标签的正则表达式
        String regEx_html = "<[^>]+>";

        Pattern p_script = Pattern.compile(scriptRegex, Pattern.CASE_INSENSITIVE);
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

}
