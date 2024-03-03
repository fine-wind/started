package com.example.common.v0.utils;

import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Log4j2
public class DateUtil {

    public final static String pattern = "yyyy-MM-dd";
    public final static SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    public final static SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

    private static DateTimeFormatter getDateTimeFormatter() {
        return dateFormatter;
    }

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDate() {
        String dateString = formatter2.format(new Date());
        ParsePosition pos = new ParsePosition(8);
        return formatter2.parse(dateString, pos);
    }

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 时间格式化
     *
     * @param date 要格式化的时间
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static String toString(@NotNull Date date) {
        return formatter2.format(date);
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        String dateString = formatter.format(new Date());
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(dateString, pos);
    }

    /**
     * 获取现在时间
     *
     * @return 返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }

    /**
     * 获取现在时间
     *
     * @return 返回字符串格式 yyyyMMddHHmmss
     */
    public static String getStringAllDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return formatter.format(new Date());
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort() {
        return formatter.format(new Date());
    }

    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     *
     * @return .
     */
    public static String getTimeShort() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(new Date());
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate .
     * @return .
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate .
     * @param def     转换失败后返回该值
     * @return .
     */
    public static Date strToDateLong(String strDate, String def) {
        Date r;
        try {
            r = strToDateLong(strDate);
        } catch (Exception exp) {
            r = null;
        }
        if (r == null)
            return strToDateLong(def);
        else
            return r;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     *
     * @param dateDate
     * @param
     * @return
     */
    public static String dateToStr(Date dateDate) {
        if (dateDate == null) {
            return null;
        }
        try {
            return formatter.format(dateDate);
        } catch (NullPointerException e) {
            log.error(e);
            return null;
        }
    }

    public static String dateToStr(LocalDate dateDate) {
        return dateFormatter.format(dateDate);
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate .
     * @return .
     */
    public static Date strToDate(String strDate) {
        if (StringUtil.isEmpty(strDate)) {
            return null;
        }
        ParsePosition pos = new ParsePosition(0);
        try {
            return formatter.parse(strDate, pos);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM
     *
     * @param strdate .
     * @return .
     */
    public static Date strToShortDate(String strdate) {
        String pattern = "yyyy-MM";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        ParsePosition pos = new ParsePosition(0);
        try {
            return formatter.parse(strdate, pos);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd格式字符串
     *
     * @param strDate 要转换成时间的字符串
     * @return 转换成功返回yyyy-MM-dd格式字符串,失败返回null
     */
    public static String strToDateStr(String strDate) {
        return DateUtil.dateToStr(DateUtil.strToDate(strDate));
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate .
     * @return .
     */
    public static Timestamp strToDateSql(String strDate) {
        if (StringUtil.isEmpty(strDate)) {
            return null;
        }
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter2.parse(strDate, pos);
        return new Timestamp(strtodate.getTime());
    }

    /**
     * 得到现在时间
     *
     * @return .
     */
    public static Date getNow() {
        return new Date();
    }

    /**
     * 与今天相距几天的时间是哪天？
     *
     * @param day .
     * @return .
     */
    public static Date getLastDate(long day) {
        Date date = new Date();
        long date_3_hm = date.getTime() - 3600000 * 34 * day;
        return new Date(date_3_hm);
    }

    /**
     * 获取某月第一天
     *
     * @param date 某个月，null 时为现在
     * @return return
     */
    public static Date getMonthFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date == null ? new Date() : date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        return calendar.getTime();
    }

    /**
     * 获取某月最后一刻
     *
     * @param date 某个月，null 时为现在
     * @return return
     */
    public static Date getMonthLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date == null ? new Date() : date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        return calendar.getTime();
    }

    /**
     * 得到现在时间
     *
     * @return 字符串 yyyyMMdd HHmmss
     */
    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
        return formatter.format(currentTime);
    }

    /**
     * 功能：<br/>
     *
     * @author Tony
     */
    public static String getTodayShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(currentTime);
    }


    /**
     * 得到现在小时
     */
    public static String getHour() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String hour;
        hour = dateString.substring(11, 13);
        return hour;
    }

    /**
     * 得到现在分钟
     *
     * @return .
     */
    public static String getTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String min;
        min = dateString.substring(14, 16);
        return min;
    }

    /**
     * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
     *
     * @param sformat yyyyMMddhhmmss
     * @return .
     */
    public static String getUserDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        return formatter.format(currentTime);
    }

    /**
     * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
     */
    public static String getTwoHour(String st1, String st2) {
        String[] kk = null;
        String[] jj = null;
        kk = st1.split(":");
        jj = st2.split(":");
        if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
            return "0";
        else {
            double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
            double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
            if ((y - u) > 0)
                return y - u + "";
            else
                return "0";
        }
    }

    /**
     * 得到二个日期间的间隔天数
     */
    public static String getTwoDay(String sj1, String sj2) {
        long day = 0;
        try {
            Date date = formatter.parse(sj1);
            Date mydate = formatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 得到二个日期间的间隔天数
     *
     * @param start .
     * @param end   .
     * @return 计算失败返回 0
     */
    public static long getTwoDay(Date start, Date end) {
        long day = 0;
        try {
            day = (start.getTime() - end.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            log.error(e);
            return 0;
        }
        return day;
    }

    /**
     * 得到二个日期间的间隔分钟数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 计算失败返回 0
     */
    public static long diffToMinutes(Date start, Date end) {
        long minutes = 0;
        try {
            minutes = (end.getTime() - start.getTime()) / (60 * 1000);
        } catch (Exception e) {
            log.error(e);
        }
        return minutes;
    }

    /**
     * 时间前推或后推分钟,其中JJ表示分钟.
     */
    public static String getPreTime(String sj1, String jj) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mydate1 = "";
        try {
            Date date1 = format.parse(sj1);
            long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60L;
            date1.setTime(Time * 1000);
            mydate1 = format.format(date1);
        } catch (Exception e) {
            log.error(e);
        }
        return mydate1;
    }

    /**
     * 时间前推或后推分钟,其中JJ表示分钟.
     */
    public static Date getPreTime(Date sj1, int jj) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return new Date((sj1.getTime()) + (long) jj * 60 * 1000);
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate(yyyy-mm-dd)为时间,delay为前移或后延的天数
     */
    public static String getNextDay(String nowdate, String delay) {
        try {
            return getNextDay(nowdate, Integer.parseInt(delay));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate(yyyy-mm-dd)为时间,delay为前移或后延的天数
     */
    public static String getNextDay(String nowdate, int delay) {
        try {
            Date d = strToDate(nowdate);
            return DateUtil.dateToStr(getNextDay(d, delay));
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * 得到一个时间延后或前移几天的时间
     *
     * @param nowdate 时间
     * @param delay   前移或后延的天数
     * @return 前移或后延之后的时间
     */
    public static Date getNextDay(Date nowdate, int delay) {
        try {
            long myTime = (nowdate.getTime() / 1000) + (long) delay * 24 * 60 * 60;
            return new Date(myTime * 1000);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 功能：<br/> 距离现在几天的时间是多少
     * 获得一个时间字符串，格式为：yyyy-MM-dd HH:mm:ss
     * day  如果为整数，表示未来时间
     * 如果为负数，表示过去时间
     *
     * @author Tony
     * @version 2016年11月29日 上午11:02:56 <br/>
     */
    public static String getFromNow(int day) {
        return formatter2.format(getFromNowToDate(day));
    }

    /**
     * 功能：距离现在几天的时间是多少<br/>
     *
     * @param day >0:表示未来时间;<0:表示过去时间;
     * @return 计算后的时间
     */
    public static Date getFromNowToDate(int day) {
        Date date = new Date();
        long dateTime = (date.getTime() / 1000) + (long) day * 24 * 60 * 60;
        date.setTime(dateTime * 1000);
        return date;
    }

    /**
     * 判断是否润年
     *
     * @param ddate .
     * @return .
     */
    public static boolean isLeapYear(String ddate) {

        /*
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
         * 3.能被4整除同时能被100整除则不是闰年
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(Objects.requireNonNull(strToDate(ddate)));
        int year = gc.get(Calendar.YEAR);
        if ((year % 400) == 0)
            return true;
        else if ((year % 4) == 0) {
            return (year % 100) != 0;
        } else
            return false;
    }

    /**
     * 返回美国时间格式 26 Apr 2006
     *
     * @param str .
     * @return .
     */
    public static String getEDate(String str) {
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);
        String j = strtodate.toString();
        String[] k = j.split(" ");
        return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
    }

    /**
     * 获取一个月的最后一天
     *
     * @param dat .
     * @return .
     */
    public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd
        String str = dat.substring(0, 8);
        String month = dat.substring(5, 7);
        int mon = Integer.parseInt(month);
        if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
            str += "31";
        } else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
            str += "30";
        } else {
            if (isLeapYear(dat)) {
                str += "29";
            } else {
                str += "28";
            }
        }
        return str;
    }

    /**
     * 判断二个时间是否在同一个周
     *
     * @param date1 .
     * @param date2 .
     * @return .
     */
    public static boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }

    /**
     * 产生周序列,即得到当前时间所在的年度是第几周
     *
     * @return .
     */
    public static String getSeqWeek() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        if (week.length() == 1)
            week = "0" + week;
        String year = Integer.toString(c.get(Calendar.YEAR));
        return year + week;
    }

    /**
     * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
     *
     * @param sdate .
     * @param num .
     * @return .
     */
    public static String getWeek(String sdate, String num) {
        // 再转换为时间
        Date dd = DateUtil.strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(dd);
        // 返回星期一所在的日期
        if ("1".equals(num)) {
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        }
        // 返回星期二所在的日期
        else if ("2".equals(num)) {
            c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        }
        // 返回星期三所在的日期
        else if ("3".equals(num)) {
            c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        }
        // 返回星期四所在的日期
        else if ("4".equals(num)) {
            c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        }// 返回星期五所在的日期
        else if ("5".equals(num)) {
            c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        }// 返回星期六所在的日期
        else if ("6".equals(num)) {
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        } // 返回星期日所在的日期
        else if ("0".equals(num)) {
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 根据一个日期，返回年份的字符串
     *
     * @param sdate .
     * @return .
     */
    public static String getYear(String sdate) {
        // 再转换为时间
        Date date = DateUtil.strToDateLong(sdate, null);
        return getYear(date);
    }

    /**
     * 根据一个日期，返回年份的字符串
     *
     * @param date 日期
     * @return 该日期的年份
     */
    public static String getYear(Date date) {
        // 再转换为时间
        if (date == null) {
            return "";
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("yyyy").format(c.getTime());
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate .
     * @return .
     */
    public static String getWeek(String sdate) {
        // 再转换为时间
        Date date = DateUtil.strToDate(sdate);
        Calendar c = Calendar.getInstance();
        assert date != null;
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    public static String getWeekStr(String sdate) {
        String str = "";
        str = DateUtil.getWeek(sdate);
        str = switch (str) {
            case "1" -> "星期日";
            case "2" -> "星期一";
            case "3" -> "星期二";
            case "4" -> "星期三";
            case "5" -> "星期四";
            case "6" -> "星期五";
            case "7" -> "星期六";
            default -> str;
        };
        return str;
    }

    /**
     * 两个时间之间的天数
     *
     * @param date1 .
     * @param date2 .
     * @return .
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.isEmpty())
            return 0;
        if (date2 == null || date2.isEmpty())
            return 0;
        // 转换为标准时间
        Date date = null;
        Date mydate = null;
        try {
            date = formatter.parse(date1);
            mydate = formatter.parse(date2);
        } catch (Exception e) {
            log.error(e);
        }
        assert mydate != null;
        return (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * 两个时间之间的天数
     *
     * @param date1 减数
     * @param date2 被减数
     * @return .
     */
    public static Long getDays(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) / (1000 * 3600 * 24);
    }


    /**
     * 形成如下的日历 ， 根据传入的一个时间返回一个结构 星期日 星期一 星期二 星期三 星期四 星期五 星期六 下面是当月的各个时间
     * 此函数返回该日历第一行星期日所在的日期
     *
     * @param sdate .
     * @return .
     */
    public static String getNowMonth(String sdate) {
        // 取该时间所在月的一号
        sdate = sdate.substring(0, 8) + "01";

        // 得到这个月的1号是星期几
        Date date = DateUtil.strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int u = c.get(Calendar.DAY_OF_WEEK);
        return DateUtil.getNextDay(sdate, (1 - u) + "");
    }

    /**
     * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
     *
     * @param k 表示是取几位随机数，可以自己定
     */

    public static String getNo(int k) {

        return getUserDate("yyyyMMddhhmmss") + NumberUtils.getRandom(k);
    }


    public static boolean RightDate(String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        ;
        if (date == null)
            return false;
        if (date.length() > 10) {
            sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        try {
            sdf.parse(date);
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    /***************************************************************************
     * //nd=1表示返回的值中包含年度 //yf=1表示返回的值中包含月份 //rq=1表示返回的值中包含日期 //format表示返回的格式 1
     * 以年月日中文返回 2 以横线-返回 // 3 以斜线/返回 4 以缩写不带其它符号形式返回 // 5 以点号.返回
     **************************************************************************/
    public static String getStringDateMonth(String sdate, String nd, String yf, String rq, String format) {
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        String s_nd = dateString.substring(0, 4); // 年份
        String s_yf = dateString.substring(5, 7); // 月份
        String s_rq = dateString.substring(8, 10); // 日期
        String sreturn = "";
        //roc.util.MyChar mc = new roc.util.MyChar();
        //if (sdate == null || sdate.equals("") || !mc.Isdate(sdate)) { // 处理空值情况
        if (sdate == null || sdate.equals("")) {
            if (nd.equals("1")) {
                sreturn = s_nd;
                // 处理间隔符
                if (format.equals("1"))
                    sreturn = sreturn + "年";
                else if (format.equals("2"))
                    sreturn = sreturn + "-";
                else if (format.equals("3"))
                    sreturn = sreturn + "/";
                else if (format.equals("5"))
                    sreturn = sreturn + ".";
            }
            // 处理月份
            if (yf.equals("1")) {
                sreturn = sreturn + s_yf;
                if (format.equals("1")) {
                    sreturn = sreturn + "月";
                } else if (format.equals("2")) {
                    sreturn = sreturn + "-";
                } else if (format.equals("3"))
                    sreturn = sreturn + "/";
                else if (format.equals("5"))
                    sreturn = sreturn + ".";
            }
            // 处理日期
            if (rq.equals("1")) {
                sreturn = sreturn + s_rq;
                if (format.equals("1"))
                    sreturn = sreturn + "日";
            }
        } else {
            // 不是空值，也是一个合法的日期值，则先将其转换为标准的时间格式
            sdate = getOKDate(sdate);
            s_nd = sdate.substring(0, 4); // 年份
            s_yf = sdate.substring(5, 7); // 月份
            s_rq = sdate.substring(8, 10); // 日期
            if (nd.equals("1")) {
                sreturn = s_nd;
                // 处理间隔符
                if (format.equals("1"))
                    sreturn = sreturn + "年";
                else if (format.equals("2"))
                    sreturn = sreturn + "-";
                else if (format.equals("3"))
                    sreturn = sreturn + "/";
                else if (format.equals("5"))
                    sreturn = sreturn + ".";
            }
            // 处理月份
            if (yf.equals("1")) {
                sreturn = sreturn + s_yf;
                if (format.equals("1"))
                    sreturn = sreturn + "月";
                else if (format.equals("2"))
                    sreturn = sreturn + "-";
                else if (format.equals("3"))
                    sreturn = sreturn + "/";
                else if (format.equals("5"))
                    sreturn = sreturn + ".";
            }
            // 处理日期
            if (rq.equals("1")) {
                sreturn = sreturn + s_rq;
                if (format.equals("1"))
                    sreturn = sreturn + "日";
            }
        }
        return sreturn;
    }

    public static String getNextMonthDay(String sdate, int m) {
        sdate = getOKDate(sdate);
        int year = Integer.parseInt(sdate.substring(0, 4));
        int month = Integer.parseInt(sdate.substring(5, 7));
        month = month + m;
        if (month < 0) {
            month = month + 12;
            year = year - 1;
        } else if (month > 12) {
            month = month - 12;
            year = year + 1;
        }
        String smonth = "";
        if (month < 10)
            smonth = "0" + month;
        else
            smonth = "" + month;
        return year + "-" + smonth + "-10";
    }

    /**
     * 功能：<br/>
     *
     * @author Tony
     * @version 2015-3-31 上午09:29:31 <br/>
     */
    public static String getOKDate(String sdate) {
        if (sdate == null || sdate.isEmpty())
            return getStringDateShort();

//      if (!VeStr.Isdate(sdate)) {
//       sdate = getStringDateShort();
//      }
//      // 将“/”转换为“-”
//      sdate = VeStr.Replace(sdate, "/", "-");
        // 如果只有8位长度，则要进行转换
        if (sdate.length() == 8)
            sdate = sdate.substring(0, 4) + "-" + sdate.substring(4, 6) + "-" + sdate.substring(6, 8);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(sdate, pos);
        return formatter.format(strtodate);
    }

    /**
     * 获取当前时间的前一天时间
     *
     * @param cl .
     * @return .
     */
    private static String getBeforeDay(Calendar cl) {
        //使用roll方法进行向前回滚
        //cl.roll(Calendar.DATE, -1);
        //使用set方法直接进行设置
        // int day = cl.get(Calendar.DATE);
        cl.add(Calendar.DATE, -1);
        return formatter.format(cl.getTime());
    }

    /**
     * 获取当前时间的后一天时间
     *
     * @param cl .
     * @return .
     */
    private static String getAfterDay(Calendar cl) {
        //使用roll方法进行回滚到后一天的时间
        //cl.roll(Calendar.DATE, 1);
        //使用set方法直接设置时间值
        //int day = cl.get(Calendar.DATE);
        cl.add(Calendar.DATE, 1);
        return formatter.format(cl.getTime());
    }

    private static String getDateAMPM() {
        GregorianCalendar ca = new GregorianCalendar();
        //结果为“0”是上午     结果为“1”是下午
        int i = ca.get(GregorianCalendar.AM_PM);
        return i == 0 ? "AM" : "PM";
    }

    private static int compareToDate(String date1, String date2) {
        return date1.compareTo(date2);
    }

    private static int compareToDateString(String date1, String date2) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int i = 0;
        try {
            long ldate1 = formatter.parse(date1).getTime();
            long ldate2 = formatter.parse(date2).getTime();
            i = Long.compare(ldate1, ldate2);

        } catch (ParseException e) {
            log.error(e);
        }
        return i;
    }

    public static String[] getFiveDate() {
        String[] dates = new String[2];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String five = " 05:00:00";

        if (getDateAMPM().equals("AM") && compareToDateString(getStringDate(), getStringDateShort() + five) == -1) {
            dates[0] = getBeforeDay(calendar) + five;
            dates[1] = getStringDateShort() + five;
        } else {
            dates[0] = getStringDateShort() + five;
            dates[1] = getAfterDay(calendar) + five;
        }

        return dates;
    }

    public static String getFiveDate2() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String five = " 05:00:00";
        String reStr = "";
        if (getDateAMPM().equals("AM") && compareToDateString(getStringDate(), getStringDateShort() + five) == -1) {
            reStr = getBeforeDay(calendar);
        } else {
            reStr = getStringDateShort();
        }
        return reStr;
    }

    /**
     * 每周的第一天和最后一天
     *
     * @return .
     */
    public static Date[] getFirstAndLastOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        // 所在周开始日期
        Date data1 = cal.getTime();
        cal.add(Calendar.DAY_OF_WEEK, 6);
        // 所在周结束日期
        Date data2 = cal.getTime();
        return new Date[]{data1, data2};

    }

    /**
     * 获取今天的最后一秒
     *
     * @return .
     */
    public static Date getNowDateLastSecond() {
        Calendar cal = Calendar.getInstance();
        return getDateLastSecond(cal.getTime());
    }

    /**
     * 获取某一天的最后一秒
     *
     * @param date 某一天
     * @return 某一天的最后一秒
     */
    public static Date getDateLastSecond(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        cal.set(year, month, day, 23, 59, 59);
        return cal.getTime();
    }


    /**
     * 首先将 Date 转化为 yyyy-MM-dd 然后 在转化成Date
     *
     * @param date .
     * @return .
     */
    public static Date toShortDate(Date date) {
        String format = formatter.format(date);
        return strToDate(format);
    }

    /**
     * 一个时间加上n年后是什么时间
     *
     * @param date 要计算的时间 （Date）
     * @param year 加上去多少年 （int）
     * @return 加上后的时间
     */
    public static Date dateAddYear(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }


    /**
     * 当前时间与传入的时间相减 返回年份
     *
     * @param date .
     * @return .
     */
    public static int nowSubtractYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);

        int nowYear = calendar.get(Calendar.YEAR);

        int dateYear = calendar1.get(Calendar.YEAR);


        return nowYear - dateYear;
    }

    /**
     * 获取默认时间
     *
     * @return 1990-01-01
     */
    public static Date defaultTime() {
        String defaultTimeStr = "1990-01-01";
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(defaultTimeStr, pos);
    }


    /**
     * 获取当天的最后时间
     */
    public static Date getTodayLastDate() {
        LocalDateTime currentLocalDate = LocalDateTime.now();
        LocalDateTime lastTodayDate = LocalDateTime.of(currentLocalDate.getYear()
                , currentLocalDate.getMonth()
                , currentLocalDate.getDayOfMonth()
                , 23, 59, 59);
        return toDateByLocalDate(lastTodayDate);
    }


    /**
     * 把LocalDateTime 转换成 Date
     *
     * @return .
     */
    public static Date toDateByLocalDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 把LocalDateTime 转换成 str
     *
     * @return .
     */
    public static String toStringByLocalDate(LocalDateTime date, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(date);
    }

    /**
     * 格式化Date
     *
     * @param date    要格式化的时间
     * @param pattern 时间格式
     * @return 时间格式的时间
     */
    public static String toStringByPattern(Date date, String pattern) {
        java.text.DateFormat format1 = new SimpleDateFormat(pattern);
        return format1.format(date);
    }

    /**
     * 获取最大的日期
     *
     * @return 9999-12-31 23:59:59
     */
    public static Date getMaxDate() {
        return strToDateLong("9999-12-31 23:59:59");
    }

    /**
     * 获取最小的日期
     *
     * @return 1900-01-01 00:00:00
     */
    public static Date getMinDate() {
        return strToDateLong("1900-01-01 00:00:00");
    }

    public static String nowAddYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, +year);
        return dateToStr(calendar.getTime());
    }

    /**
     * 格式化时间
     *
     * @param date .
     * @return .
     */
    public static String getNiceDate(Date date) {
        if (null == date) {
            return "";
        }
        long currentTime = System.currentTimeMillis() - date.getTime();
        return getNiceDate(currentTime);
    }

    public static String getNiceDate(Long currentTime) {
        if (null == currentTime) {
            return "";
        }
        String result;
        long time = (currentTime / 1000);
        if (time < 60) {
            result = time + "秒";
        } else if (time < 3600) {
            result = time / 60 + "分钟";
        } else if (time < 86400) {
            result = time / 3600 + "小时";
        } else if (time < 172800) {
            result = time / 86400 + "天";
        } else {
            result = DateUtil.dateToStr(new Date(currentTime));
        }
        return result;
    }

}
