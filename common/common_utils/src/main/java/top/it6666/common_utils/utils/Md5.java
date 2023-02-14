package top.it6666.common_utils.utils;

import lombok.experimental.UtilityClass;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author BNTang
 */
@UtilityClass
public class Md5 {

    /**
     * <b>
     * 加密
     * </b>
     *
     * @param str 加密内容
     * @return 加密之后的秘钥内容
     */
    public String encrypt(String str) {
        try {
            char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                    '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            
            byte[] bytes = str.getBytes();

            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(bytes);

            bytes = md.digest();

            int j = bytes.length;

            char[] chars = new char[j * 2];

            int k = 0;

            for (byte b : bytes) {
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }

            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            // Generic exceptions should never be thrown

            // 不太理解，大意是说不要直接抛Error,RuntimeException/Throwable/Exception这样的通用的异常。
            // 我的具体应用是：throw new Error("Error copying database")，
            // 给出的建议是：Define and throw a dedicated exception instead of using a generic one
            // （定义并抛出一个专用的异常来代替一个通用的异常）
            throw new RuntimeException("MD5加密出错！！" + e);
        }
    }
}