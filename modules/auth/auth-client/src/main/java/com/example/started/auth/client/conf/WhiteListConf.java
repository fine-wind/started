package com.example.started.auth.client.conf;

/**
 * 返回的白名单的路径
 * 实现此类以达到白名单访问的目的
 */
public interface WhiteListConf {
    String[] whiteList();
}
