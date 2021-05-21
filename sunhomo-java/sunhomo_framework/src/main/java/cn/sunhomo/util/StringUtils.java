package cn.sunhomo.util;

import java.util.Arrays;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * 下划线
     */
    private static final char SEPARATOR = '_';

    public static int[] toIntArray(String str, String separatorChars) {
        if (isEmpty(str)) return new int[]{};
        return Arrays.stream(split(str, separatorChars)).mapToInt(num -> Integer.parseInt(num)).toArray();
    }

    /**
     * 下划线转驼峰命名
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase = true;
        // 当前字符是否大写
        boolean curreCharIsUpperCase = true;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
                sb.append(SEPARATOR);
            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    public static boolean isNotnull(Object object) {
        return !isNull(object);
    }

    private static boolean isNull(Object object) {
        return object == null;
    }
}
