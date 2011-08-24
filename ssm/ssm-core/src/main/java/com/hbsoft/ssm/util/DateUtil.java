package com.hbsoft.ssm.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Handle date
 * @author Thanh Hoang
 *
 */

public class DateUtil {
	
	/**
     * Convert a date to string
     *
     * @param dtSource
     * @return
     */
    public static String dateToStr(Date dtSource) {
        return dateToStr(dtSource, DateUtil.DATE_PATTERN);
    }   
    
	/**
     * convert a date to string with date pattern provided
     *
     * @param dtSource
     * @param pattern
     * @return
     */
    public static String dateToStr(Date dtSource, String pattern) {
        if (dtSource == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(dtSource);
    }
    
    /**
     * Convert a string to date
     *
     * @param sourceDate
     * @return
     */
    public static Date strToDate(String sourceDate) {
        return strToDate(sourceDate, DateUtil.DATE_PATTERN);
    }

    /**
     * Convert a string to date with the date pattern provided
     *
     * @param sourceDate
     * @param pattern
     * @return
     */
    public static Date strToDate(String sourceDate, String pattern) {
        try {
        	SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.parse(sourceDate);
        } catch (ParseException ex) {
            return null;
        } catch (Exception ex1) {
            return null;
        }
    }
    
    public static String getDuration(Date start, Date end) {
    	String result = null;
        try {
        		if(start.after(end)) return null;
        		NumberFormat nf = NumberFormat.getInstance();
        		nf.setMinimumIntegerDigits(2);
	        	long difference = end.getTime() - start.getTime();
	        	long second = (difference/1000);
	        	long hours = second/3600;
	        	long secondRemain = (second%3600)%60;
	        	long minRemain = (second%3600)/60;
	        	result = "" + nf.format(hours) + ":" + nf.format(minRemain) + ":" + nf.format(secondRemain);
	        	return result;
        } catch (Exception ex) {
            return result;
        }
    }

	public static final String DATE_PATTERN = "MM/dd/yyyy";
	public static final String DATE_TIME_PATTERN = "MM/dd/yyyy hh:mm:ss";
}