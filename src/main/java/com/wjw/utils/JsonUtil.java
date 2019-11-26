package com.wjw.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 将对象转化为json格式
 * @author : wjwjava@163.com
 * @date : 23:23 2019/11/18
 */
public class JsonUtil {
    /**
     * 将对象转化为json格式
     * @param object
     * @return
     */
    public static String objToJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
