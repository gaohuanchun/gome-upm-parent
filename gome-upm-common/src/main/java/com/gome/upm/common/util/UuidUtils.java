package com.gome.upm.common.util;

import java.util.Random;

/**
 * UUid 工具类
 */
public class UuidUtils {
    private final static Random R = new Random();

    //26 字母+ 10 数字，去掉 0,O,1,L 剩 32数字。
    private final static char[] CHARS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9'};

    private final static int CHARS_LENGTH = CHARS.length;

    /**
     * 生成一个随机的6个字符字符串
     *
     * @return 6个字符字符串
     */
    public static String generateNew() {
        return generateWithLength(6);
    }

    public static String generateWithLength(int length) {
        if (length > 0) {
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                sb.append(CHARS[R.nextInt(CHARS_LENGTH)]);
            }
            return sb.toString();
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(Math.pow(CHARS_LENGTH, 6.0));
        System.out.println(UuidUtils.generateNew());
        System.out.println(UuidUtils.generateNew());
        System.out.println(UuidUtils.generateNew());
        System.out.println(UuidUtils.generateNew());
        System.out.println(UuidUtils.generateNew());
    }
}
