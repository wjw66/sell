package com.wjw.utils;

/**
 * @author : wjwjava@163.com
 * @date : 23:11 2019/11/20
 */
public class MathUtil {
    private static final double MONEY_RANGE = 0.01;

    /**
     * 比较两个金额在一定允许的误差内是否相等
     * @param d1
     * @param d2
     * @return
     */
    public static boolean equalsMoney(Double d1,Double d2){
        double moneyResult = Math.abs(d1 - d2);
        if (moneyResult < MONEY_RANGE){
            return true;
        }else {
            return false;
        }
    }
}
