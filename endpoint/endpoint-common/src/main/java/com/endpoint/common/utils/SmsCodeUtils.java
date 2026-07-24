package com.endpoint.common.utils;

import java.util.Random;

/**
 * @author cr
 * @date 2022/11/11
 * @description
 */
public class SmsCodeUtils {
    public static String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }
}
