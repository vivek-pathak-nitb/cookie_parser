package com.quantcast.cookie.command;

import com.quantcast.cookie.TestUtil;
import com.quantcast.cookie.reader.CookieLogFileReader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GetActiveCookiesByDateTest {

  private static final String COOKIE_CSV = "cookie.csv";
  private static final String DATE_STRING = "2018-12-09";

  private GetActiveCookiesByDate getActiveCookiesByDate;

  @Before
  public void setup() throws IOException {
    CookieLogFileReader cookieLogFileReader =
        new CookieLogFileReader(TestUtil.getResourceFileAbsolutePath(COOKIE_CSV));
    getActiveCookiesByDate = new GetActiveCookiesByDate(cookieLogFileReader);
  }

  @Test
  public void testGetActiveCookiesByDate() throws ParseException {
    // execute
    List<String> activeCookies =
        getActiveCookiesByDate.apply(CookieCommand.DATE_FORMAT.parse(DATE_STRING));

    // assert
    assertEquals(Collections.singletonList("AtY0laUfhglK3lC7"), activeCookies);
  }

  @Test
  public void testGetActiveCookiesByDate_exception() {
    // execute
    List<String> activeCookies = getActiveCookiesByDate.apply(null);

    // assert
    assertEquals(Collections.emptyList(), activeCookies);
  }
}
