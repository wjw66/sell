package com.wjw.utils;

import java.util.UUID;

public class KeyUtil {

    public static synchronized String genUnique(){
       String random =  UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    return System.currentTimeMillis() + random;
    }
}
