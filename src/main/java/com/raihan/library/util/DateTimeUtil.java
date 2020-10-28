package com.raihan.library.util;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateTimeUtil {

  public static Date addDays(Date date, int days) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.DAY_OF_MONTH, days);
    return new Date(cal.getTime().getTime());
  }

  public static long dayDifference(Date date1, Date date2) {
    long diffInMillies = date1.getTime() - date2.getTime();
    return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
  }


}
