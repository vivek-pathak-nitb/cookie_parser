package com.quantcast.cookie.command;

import com.quantcast.cookie.TestUtil;
import org.junit.Test;

public class CookieCommandTest {

  private static final String COOKIE_CSV = "cookie.csv";

  @Test
  public void testCookieCommand() {
    String[] args = {"-f", TestUtil.getResourceFileAbsolutePath(COOKIE_CSV), "-d", "2018-12-09"};
    CookieCommand.main(args);
  }
}
