package com.quantcast.cookie.command;

import com.quantcast.cookie.reader.CookieLogFileReader;
import picocli.CommandLine;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/** Driver class for the command. */
@CommandLine.Command(name = "cookieCli", description = "Performs operations on Cookie log")
public class CookieCommand implements Runnable {

  public static final SimpleDateFormat DATE_FORMAT;

  static {
    DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  @CommandLine.Option(names = "-f", description = "Filename to process", required = true)
  private String fileName;

  @CommandLine.Option(names = "-d", description = "Date", required = true)
  private String dateString;

  public void run() {
    try {
      CookieLogFileReader cookieLogFileReader = new CookieLogFileReader(fileName);
      GetActiveCookiesByDate getActiveCookiesByDate =
          new GetActiveCookiesByDate(cookieLogFileReader);
      Date providedDate = DATE_FORMAT.parse(dateString);
      for (String cookie : getActiveCookiesByDate.apply(providedDate)) {
        System.out.println(cookie);
      }
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }

  public static void main(String[] args) {
    CommandLine.run(new CookieCommand(), args);
  }
}
