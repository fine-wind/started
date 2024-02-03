package com.example.started.auth.role.user;

/**
 * 用户
 */
public class CurrentUser {

    final static ThreadLocal<String> LOCAL = new ThreadLocal<>();

    public static void setUserName(String userName) {
        LOCAL.set(userName);
    }

}
