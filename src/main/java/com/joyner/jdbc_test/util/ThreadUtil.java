package com.joyner.jdbc_test.util;

public class ThreadUtil {

    /**
     * 休息，单位是秒
     *
     * @param l
     */
    public static void sleepSecond(long l) {
        try {
            Thread.sleep(l * 1000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * 休息，单位是毫秒
     *
     * @param l
     */
    public static void sleepMillis(long l) {
        try {
            Thread.sleep(l );
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


}
