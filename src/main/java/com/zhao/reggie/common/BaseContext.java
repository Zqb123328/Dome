package com.zhao.reggie.common;
//基于ThreadLocal封装工具类，用户获取和保存当前用户的id
public class BaseContext {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();

    //添加值
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    //获取值
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
