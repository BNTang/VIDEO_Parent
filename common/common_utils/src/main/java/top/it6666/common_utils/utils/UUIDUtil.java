/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.common_utils.utils;

import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description UUID工具类
 * @since Created in 2021/6/6 006 9:20
 **/
public class UUIDUtil {
    /**
     * 生成指定位数的随机字符串 (纯数字)
     *
     */
    public static String getRandomNumber(Integer number) {
        return RandomStringUtils.randomNumeric(number);
    }

    /**
     * 生成随机[a-z]字符串，包含大小写
     */
    public static String getRandomString(Integer number) {
        return RandomStringUtils.randomAlphabetic(number);
    }

    /**
     * 生成从ASCII 32到126组成的随机字符串
     */
    public static String getRandomAscii(Integer number) {
        return RandomStringUtils.randomAscii(number);
    }

    /**
     * 生成没有 "-" 的uuid随机字符串 (包含字母和数字)
     */
    public static String getUuid() {
        // replace("-","") : 把 "-" 该为空字符串 ""
        return UUID.randomUUID().toString().replace("-", "");
    }
}