package com.example.common.utils;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 下载工具类
 *
 * @author 行星
 * @date 2020/4/24 17:38
 */
public class DownloadUtils {
    /**
     * @param response response
     * @param fileName filename
     * @return
     * @throws IOException
     */
    public static Result<Object> download(HttpServletResponse response, String fileName) throws IOException {

        if (fileName == null) {
            return null;
        }
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }
        return download(response, file);
    }

    public static Result<Object> download(HttpServletResponse response, File file) throws IOException {
        // 配置文件下载
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        // 下载文件能正常显示中文
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        // 实现文件下载
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            System.out.println("Download the song successfully!");
        } catch (Exception e) {
            System.out.println("Download the song failed!");
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
