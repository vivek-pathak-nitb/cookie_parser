package com.quantcast.cookie.command;

import com.quantcast.cookie.reader.CookieLogReader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.function.Function;

public class GetActiveCookiesByDate implements Function<Date, List<String>> {

  private static final SimpleDateFormat UTC_FORMAT;

  static {
    UTC_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.ENGLISH);
    UTC_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  private final CookieLogReader cookieLogReader;

  public GetActiveCookiesByDate(final CookieLogReader cookieLogReader) {
    this.cookieLogReader = cookieLogReader;
  }

  @Override
  public List<String> apply(Date date) {
    try {
      String line;
      Map<String, Integer> cookieCountMap = new HashMap<>();
      // skip first line
      cookieLogReader.read();
      while ((line = cookieLogReader.read()) != null) {
        if (line.isEmpty()) {
          continue;
        }

        String[] arr = line.split(",");
        Date logDate = UTC_FORMAT.parse(arr[1]);
        Calendar cal1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Calendar cal2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal1.setTime(logDate);
        cal2.setTime(date);
        if (isEqual(cal1, cal2)) {
          cookieCountMap.put(arr[0], cookieCountMap.getOrDefault(arr[0], 0) + 1);
        }
      }

      return getMostActiveCookies(cookieCountMap);
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
      return new ArrayList<>();
    }
  }

  private List<String> getMostActiveCookies(Map<String, Integer> cookieCountMap) {
    if (cookieCountMap.size() == 0) {
      return new ArrayList<>();
    }

    List<String> activeCookies = new ArrayList<>();
    int maxCount = (Collections.max(cookieCountMap.values()));
    for (Map.Entry<String, Integer> entry : cookieCountMap.entrySet()) {
      if (entry.getValue() == maxCount) {
        activeCookies.add(entry.getKey());
      }
    }
    return activeCookies;
  }

  private boolean isEqual(Calendar cal1, Calendar cal2) {
    return cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
        && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
        && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
  }
}
