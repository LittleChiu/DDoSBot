package me.yeoc.ddosbot.util;

public class StringUtil {
    
    public static String join(Object[] array, String separator) {
        return join(array, separator, 0, array.length);
    }

   
    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (endIndex - startIndex <= 0) {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                builder.append(separator);
            }
            builder.append(array[i]);
        }
        return builder.toString();
    }
}
