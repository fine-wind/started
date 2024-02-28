package com.example.started.auth.client.user;

/**
 * 用户
 */
public class CurrentUser {

    final static ThreadLocal<String> LOCAL = new ThreadLocal<>();

    public static void setUserName(String userName) {
        LOCAL.set(userName);
    }

}
