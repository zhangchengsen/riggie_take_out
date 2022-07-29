package com.chanokh.reggie.common;

public class BaseContext {
    private static  ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }
    public static Long getCuurentId() {
        return threadLocal.get();
    }
}
