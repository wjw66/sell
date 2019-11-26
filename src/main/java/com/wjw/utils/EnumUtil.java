package com.wjw.utils;

import com.wjw.enums.CodeEnum;

/**
 * @author : wjwjava@163.com
 * @date : 10:16 2019/11/25
 * @description :
 */
/**
 * 通过code编码获取message信息
 */
public class EnumUtil {
    /**
     * 通过code编码获取枚举对象
     * @param code   枚举的编码
     * @param tClass  枚举类
     * @param <T>
     * @return
     */
    public static<T extends CodeEnum> T getByCode(Integer code, Class<T> tClass){
        for(T each:tClass.getEnumConstants()){
            if(code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
