package com.fraud.apps.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class FormatterDate {
    public final static String formatTime = "dd MM yyyy; HH:mm";
    public final static String formatDateWithoutTime = "dd MMMM yyyy";
    public final static String formatDateParameter = "yyyy-MM-dd";
    public final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", new Locale("in"));
    public final static SimpleDateFormat formatInd = new SimpleDateFormat("dd/MM/yyyy : HH:mm", new Locale("in"));
    public final static SimpleDateFormat formatWithTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    public final static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
    public final static SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm:ss");

    static final long SECOND = 1000;
    static final long MINUTE = SECOND * 60;
    static final long HOUR = MINUTE * 60;
    public static final long DAY = HOUR * 24;
    static final long WEEK = DAY * 7;

    public static final String changeDate(String sDate) {
        String result = "";
        if (sDate != null) {
            try {
                Date date = formatWithTime.parse(sDate);
                result = format.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static final Date stringToDate(String sDate) {
        if (sDate != null) {
            try {
                return formatWithTime.parse(sDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static final long dateToLong(String sDate) {
        try {
            Date d = formatDate.parse(sDate);
            long milliseconds = d.getTime();
            return milliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static final long dateToLong(String sDate, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            Date d = simpleDateFormat.parse(sDate);
            long milliseconds = d.getTime();
            return milliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static final String formatDate(long date) {
        return format.format(new Date(date));
    }

    public static final String formatDateTime(long date) {
        return formatWithTime.format(new Date(date));
    }

    public static final String formatDateBidding(long date) {
        date *= 1000;
        return formatInd.format(new Date(date));
    }

    public static final String formatHour(long time) {
        return formatHour.format(new Date(time));
    }

    public static long parseDataApiToLong(String date) {
        date = date != null ? date : "0";
        boolean contains = date.contains(".");
        String split = date;
        if (contains)
            split = date.split("\\.")[0];
        return Long.parseLong(split) * 1000;
    }

    public static final String formatDate(long date, String format) {
        SimpleDateFormat formatDate = new SimpleDateFormat(format,
                new Locale("in"));
        return formatDate.format(new Date(date));
    }

    public static final String formatDate(String date, String format) {
        SimpleDateFormat formatDate = new SimpleDateFormat(format,
                new Locale("in"));
        return formatDate.format(new Date(dateToLong(date, format)));
    }

    public static final String formatDateUsingParse(String date, String format) {
        TimeZone timeZone = TimeZone.getDefault();
        SimpleDateFormat formatDate = new SimpleDateFormat(format,
                new Locale("en"));
        formatDate.setTimeZone(timeZone);
        return formatDate.format(new Date(parseDataApiToLong(date)));
    }

    public static String convertUtcToLocalBidding(long utcTime) {
        formatInd.setTimeZone(TimeZone.getDefault());
        return formatInd.format(new Date(utcTime * 1000));
    }

    public static String getRemainingTime(long millisUntilFinished, boolean hourOnly) {

        long hours = millisUntilFinished / HOUR;
        long minutes = (millisUntilFinished % HOUR) / MINUTE;
        long seconds = (millisUntilFinished % MINUTE) / SECOND;

        if (hourOnly) {
            if (millisUntilFinished <= 3600000) {
                return String.valueOf(minutes);
            }
            return String.valueOf(hours);
        } else {
            return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        }
    }

    public static final String dateWithTime(String sDate) {
        String result = "";
        if (sDate != null) {
            try {
                Date date = formatInd.parse(sDate);
                result = format.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
