package com.wang.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String currentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        Date nowTime = new Date();
        return dateFormat.format(nowTime);
    }

}
