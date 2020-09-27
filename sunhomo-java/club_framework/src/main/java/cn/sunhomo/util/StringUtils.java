package cn.sunhomo.util;

import java.util.Arrays;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
    public static int[] toIntArray(String str, String separatorChars) {
        if (isEmpty(str)) return new int[]{};
        return Arrays.stream(split(str, separatorChars)).mapToInt(num -> Integer.parseInt(num)).toArray();
    }
}
