package com.hzu.jpg.commonwork.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/3/7.
 */

public class DoubleUtil {

    public static String doubleKeep2ForString(double d){
        if(d==0){
            return "0";
        }
        DecimalFormat format=new DecimalFormat("#.00");
        return format.format(d);
    }

    public static double doubleKeep2(double d){
        if(d==0){
            return 0.0;
        }
        DecimalFormat format=new DecimalFormat("#.00");
        return Double.parseDouble(format.format(d));
    }



    public static double DoubleSubtract(double big,double small){
        BigDecimal bigDecimal=new BigDecimal(big);
        BigDecimal smallDecimal=new BigDecimal(small);
        return doubleKeep2(bigDecimal.subtract(smallDecimal).doubleValue());

    }

    public static double doubleAdd(double a,double b){
        BigDecimal doubleFirst=new BigDecimal(a);
        BigDecimal doubleSecond=new BigDecimal(b);
        return doubleKeep2(doubleFirst.add(doubleSecond).doubleValue());
    }
}
