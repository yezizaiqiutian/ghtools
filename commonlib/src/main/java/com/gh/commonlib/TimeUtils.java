package com.gh.commonlib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    /**
     * 获取当前月份和日 7.13
     */
    public static String getMonthAndDay(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return month+"."+day;
    }

    /**
     * 获取指定格式的日期 14/7/19
     * @param s
     * @return
     */
    public static String getStringToDate(String s){
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yy/MM/dd");
        try {
            Date parse = sdf1.parse(s);
            String format = sdf2.format(parse);
            return format;
        } catch (ParseException e) {
            e.printStackTrace();
            return s;
        }
    }

    /**
     * 获取指定格式的日期 2019-07-19
     * @param s
     * @return
     */
    public static String getStringToDateOther(String s){
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = sdf1.parse(s);
            String format = sdf2.format(parse);
            return format;
        } catch (ParseException e) {
            e.printStackTrace();
            return s;
        }
    }

    /**
     * 获取指定格式的日期 2019-07-19
     * @param s
     * @return
     */
    public static String getStringDate(String s){
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = sdf1.parse(s);
            String format = sdf2.format(parse);
            return format;
        } catch (ParseException e) {
            e.printStackTrace();
            return s;
        }
    }

    /**
     * 获取指定格式的日期 2019.07.19
     * @param
     * @return
     */
    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return format.format(date);
    }

}
