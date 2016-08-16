package com.gome.upm.common.util;

import java.util.Random;

/**
 * Secret工具类
 */
public class SecretUtils {
    private final static Random R = new Random();
    private final static char[] CHARS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
            'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9'};
    private final static int CHARS_LENGTH = CHARS.length;

    /**
     * 生成一个随机的48个字符secret字符串
     *
     * @return 48个字符secret字符串
     */
    public static String generateNew() {
        return generateWithLength(48);
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
}
