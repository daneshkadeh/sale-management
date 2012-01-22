package com.s3s.ssm.util;

import org.apache.commons.lang.StringUtils;

/**
 * Handle string
 * 
 * @author Thanh Hoang
 * 
 */
public class HBStringUtils extends StringUtils {
    public static Double safeParseDouble(String str) {
        Double result;
        try {
            result = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            result = (double) 0;
        }
        return result;
    }

    public static Integer safeParseInt(String str) {
        Integer result;
        try {
            result = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            result = (int) 0;
        }
        return result;
    }
}
