package com.spring.token.util;

/**
 * Created by prkumar on 05/10/2020.
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {


    public static Date getModifiedDate(Date date) throws ParseException{

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 8);
        return calendar.getTime();

    }

    public static Date getDate(String dateString) throws ParseException{
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sd.parse(dateString);
    }

    public static Date getExpiryDate(long expiresIn) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.setTimeInMillis(calendar.getTimeInMillis()+expiresIn);
        return calendar.getTime();

    }

    public static Date getCurrentDateAndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.getTime();

    }
}


