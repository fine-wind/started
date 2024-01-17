package com.example.started.auth.client.conf;

import com.alibaba.fastjson.JSON;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SysResponseJSON {
    /**
     * 渲染到客户端
     *
     * @param object 待渲染的实体类，会自动转为json
     */
    public static void render(HttpServletRequest request, HttpServletResponse response, Object object) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        // 允许自定义请求头token(允许head跨域)
        response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        response.setHeader("Content-type", "application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.print(JSON.toJSONString(object));
        writer.flush();
        writer.close();
    }
}
