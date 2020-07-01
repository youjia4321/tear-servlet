package com.wang.utils;

public class Logger {

    public static void log(String msg){
        System.out.println("[INFO] " + DateUtil.currentTime() + " - - " + msg);
    }

}
