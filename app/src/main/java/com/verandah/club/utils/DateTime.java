package com.verandah.club.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTime {


    /*"yyyy.MM.dd G 'at' HH:mm:ss z"   2001.07.04 AD at 12:08:56 PDT
"EEE, MMM d, ''yy"	                   Wed, Jul 4, '01
"h:mm a"	                           12:08 PM
"hh 'o''clock' a, zzzz"	               12 o'clock PM, Pacific Daylight Time
"K:mm a, z"                            0:08 PM, PDT
"yyyyy.MMMMM.dd GGG hh:mm aaa"         02001.July.04 AD 12:08 PM
"EEE, d MMM yyyy HH:mm:ss Z"           Wed, 4 Jul 2001 12:08:56 -0700
"yyMMddHHmmssZ"                        010704120856-0700
"yyyy-MM-dd'T'HH:mm:ss.SSSZ"           2001-07-04T12:08:56.235-0700
"yyyy-MM-dd'T'HH:mm:ss.SSSXXX"         2001-07-04T12:08:56.235-07:00
"YYYY-'W'ww-u"                         2001-W27-3*/

    Locale locale = Locale.US;


    public static final String SERVER_DATE = "yyyy-MM-dd";
    public static final String SERVER_DATE_SLASH = "d/M/yyyy";
    public static final String SERVER_TIME = "hh:mm:ss";
    public static final String SERVER_TIME_SMALL = "hh:mma";
    public static final String SERVER_DATE_TIME = "yyyy-MM-dd hh:mm:ss";
    public static final String SERVER_DATE_PAYMENT = "MMM dd, yyyy";
    public static final String MY_TRIP_FORMAT = "MMM dd yyyy";


    public static final String DISPLAY_DATE = "MMM yyyy";
    public static final String DISPLAY_DATE_DAY = "EEE, MMM d, yyyy";
    public static final String DISPLAY_TIME = "h:mm a";
    public static final String DISPLAY_DATE_TIME = "d MMM yyyy h:mm a";
    public static final String DISPLAY_DATE_TIME_CONVERSATION = "MMM d, h:mm a";

    public static final String DISPLAY_DASH_DATE = "dd-MM-yyyy";
    public static final String DISPLAY_DATE_MONTH = "d MMM";
    public static final String DISPLAY_MONTH = "MMM";
    public static final String DISPLAY_DATE_ONLY = "d";
    public static final String DISPLAY_MONTH_YEAR = "MMMM yyyy";


    Calendar calendar;

    public DateTime() {
        calendar = Calendar.getInstance(locale);
    }

    public DateTime(String serverDateTime) {
        init(serverDateTime, SERVER_DATE_TIME);
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public DateTime(String serverDateTime, String format) {
        init(serverDateTime, format);
    }

    public DateTime(Calendar calendar) {
        this.calendar = calendar;
    }


    private void init(int year, int month, int day) {
        calendar = Calendar.getInstance(locale);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
    }

    public DateTime(int year, int month, int day) {
        init(year, month, day);
    }


    void init(String date, String format) {

        try {
            if (date.length() == SERVER_TIME.length()) {
                date = "0000-00-00 " + date;
            } else if (date.length() == SERVER_DATE.length()) {
                date += " 00:00:00";
            }
            calendar = Calendar.getInstance(locale);
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
            try {
                calendar.setTime(sdf.parse(date));
            } catch (NullPointerException e) {
                calendar = null;
            } catch (ParseException e) {
                calendar = null;
            }
        }catch (Exception e){
            calendar = null;
        }
    }


    String EMPTY = null;

    public String getDisplayDateTime() {
        return calendar == null ? EMPTY : new SimpleDateFormat(DISPLAY_DATE_TIME, locale).format(calendar.getTime());
    }

    public String getDisplayDateTimeConversation() {
        return calendar == null ? EMPTY : new SimpleDateFormat(DISPLAY_DATE_TIME_CONVERSATION, locale).format(calendar.getTime());
    }

    public String getDisplayDate() {
        return calendar == null ? EMPTY : new SimpleDateFormat(DISPLAY_DATE, locale).format(calendar.getTime());
    }

    public String getDisplayDateDay() {
        return calendar == null ? EMPTY : new SimpleDateFormat(DISPLAY_DATE_DAY, locale).format(calendar.getTime());
    }

    public String getMyTripDate() {
        return calendar == null ? EMPTY : new SimpleDateFormat(MY_TRIP_FORMAT, locale).format(calendar.getTime());
    }

    public String getDisplayTime() {
        return calendar == null ? EMPTY : new SimpleDateFormat(DISPLAY_TIME, locale).format(calendar.getTime());
    }

    public String getServerDateTime() {
        return calendar == null ? EMPTY : new SimpleDateFormat(SERVER_DATE_TIME, locale).format(calendar.getTime());
    }

    public String getServerDate() {
        return calendar == null ? EMPTY : new SimpleDateFormat(SERVER_DATE, locale).format(calendar.getTime());
    }

    public String getServerDateSlash() {
        return calendar == null ? EMPTY : new SimpleDateFormat(SERVER_DATE_SLASH, locale).format(calendar.getTime());
    }

    public String getServerTime() {
        return calendar == null ? EMPTY : new SimpleDateFormat(SERVER_TIME, locale).format(calendar.getTime());
    }

    public String getServerTimeSmall() {
        return calendar == null ? EMPTY : new SimpleDateFormat(SERVER_TIME_SMALL, locale).format(calendar.getTime());
    }

    public String getDisplayDashDate() {
        return calendar == null ? EMPTY : new SimpleDateFormat(DISPLAY_DASH_DATE, locale).format(calendar.getTime());
    }


    public String getDisplayDateMoth() {
        return calendar == null ? EMPTY : new SimpleDateFormat(DISPLAY_DATE_MONTH, locale).format(calendar.getTime());
    }


    public String getDisplayMonth() {
        return calendar == null ? EMPTY : new SimpleDateFormat(DISPLAY_MONTH, locale).format(calendar.getTime());
    }

    public String getDisplayDateOnly() {
        return calendar == null ? EMPTY : new SimpleDateFormat(DISPLAY_DATE_ONLY, locale).format(calendar.getTime());
    }

    public String getDisplayMonthYear() {
        return calendar == null ? EMPTY : new SimpleDateFormat(DISPLAY_MONTH_YEAR, locale).format(calendar.getTime());
    }

    public static String getFormatMonth(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("MM", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String getFormatYear(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        return dateFormat.format(date);
    }
    public String getDisplayBirtDay(){
        return calendar == null ? EMPTY : new SimpleDateFormat( SERVER_DATE_PAYMENT, locale).format(calendar.getTime());
    }
}
