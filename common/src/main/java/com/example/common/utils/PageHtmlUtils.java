package com.example.common.utils;

import java.util.Map;

/**
 * 分页转html工具
 */
public class PageHtmlUtils {

    private final static String pageHtmlStart = "<ul class=\"pagination\">";
    /**
     * 页面按钮<br/>
     * {0}:链接地址<br/>
     * {1}:是否不可点击<br/>
     * {2}:当前页码<br/>
     */
    private final static String pageHtmlItem = "<li><a href='%s' %s>%s</a></li>";
    private final static String pageHtmlEnd = "</ul>";
    private final static String _pageSize = "共%s页";

    /**
     * 不可点击时的样式
     */
    private final static String disabled = " style='cursor:not-allowed;'";
    /**
     * 当前页
     */
    private final static String page_index = " class='index'";

    /**
     * 页面按钮的链接
     */
    private String url;

    private String html;

    /**
     * @param index 当前页
     * @param size  每页多少行
     * @param total 总条数
     * @param url   要跳转的链接：example.com/p{0}:{0}指页码占位符
     * @param keys  {k:v} 要生成的路径的参数
     */
    public PageHtmlUtils(long index, long size, long total, String url, Map<String, Object> keys) {
        this.url = url;
        if (keys != null) {
            keys.forEach((k, v) -> {
                if (k == null || v == null) {
                    return;
                }
                this.url = this.url.replaceAll("\\{" + k + "}", String.valueOf(v));
            });
        }
        this.url = this.url.replaceAll("\\{.*?}", "%s");

        html = parsePagerHtml(index, size, total);
    }

    /**
     * @param currentPageIndex 当前第几页
     * @param totalCount       总条数
     * @return 页面的html代码
     */
    private String parsePagerHtml(long currentPageIndex, long listSize, long totalCount) {
        if (listSize == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(1024);
        /*共多少页*/
        long pageSize = totalCount / listSize;
        pageSize += totalCount % listSize > 0 ? 1 : 0;
        if (pageSize < 1) {
            return pageHtmlStart + String.format(pageHtmlItem, String.format(url, 1), disabled, 1) + pageHtmlEnd;
        } else {
            sb.append(pageHtmlStart);
            /*共多少页*/
            sb.append(String.format(pageHtmlItem, StringUtil.NULL_STRING, disabled, String.format(_pageSize, pageSize)));
            // 上一页
            currentPageIndex = Math.max(currentPageIndex, 1);
            String tiemUrl = String.format(url, currentPageIndex - 1);
            /* 是第一页吗*/
            boolean isFirst = currentPageIndex == 1;

            if (!isFirst) {
                sb.append(String.format(pageHtmlItem,
                        tiemUrl,
                        StringUtil.NULL_STRING,
                        "上一页"
                ));
                sb.append(String.format(
                        pageHtmlItem,
                        String.format(url, 1),
                        currentPageIndex < 2 ? disabled : StringUtil.NULL_STRING, "首页"
                ));
            }

            int size = 5;// 当前页 前后各几条数据
            long index = currentPageIndex - size;// 底部初始页码
            long lastpage = currentPageIndex + size;// 算出最后页码
            lastpage += index < 1 ? -index : 0; // 最前页码不足1 就让最后延后
            index -= lastpage > pageSize ? lastpage - pageSize : 0;   // 最后页码比总页码还多,

            index = Math.max(index, 1);   // 最前页码不足1 就为1
            lastpage = Math.min(lastpage, pageSize);

            for (long i = index; i <= lastpage; i++) {
                sb.append(String.format(
                        pageHtmlItem,
                        String.format(url, i),
                        currentPageIndex == i ? page_index : StringUtil.NULL_STRING,
                        i
                ));
            }
            if (currentPageIndex != pageSize) {
                sb.append(String.format(
                        pageHtmlItem,
                        currentPageIndex >= lastpage ? "" : String.format(url, currentPageIndex + 1),
                        currentPageIndex >= lastpage ? disabled : StringUtil.NULL_STRING,
                        "下一页"
                ));
                //int lastPage = totalCount / 10 + 1;// 末页
                sb.append(String.format(
                        pageHtmlItem,
                        String.format(url, pageSize),
                        currentPageIndex >= lastpage ? disabled : StringUtil.NULL_STRING,
                        "末页"
                ));
            }
            sb.append(pageHtmlEnd);

        }
        return sb.toString().replaceAll("href=''", StringUtil.NULL_STRING);
    }

    public String getHtml() {
        return html;
    }

}
