package com.quantcast.cookie.reader;

import com.quantcast.cookie.TestUtil;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CookieLogFileReaderTest {

  private static final String COOKIE_CSV = "cookie.csv";

  @Test(expected = IOException.class)
  public void testRead_invalidPath() throws IOException {
    new CookieLogFileReader("123");
  }

  @Test
  public void testRead() throws IOException {
    // set up
    CookieLogFileReader cookieLogFileReader =
        new CookieLogFileReader(TestUtil.getResourceFileAbsolutePath(COOKIE_CSV));

    // execute and assert
    for (int i = 0; i < 9; i++) {
      assertNotNull(cookieLogFileReader.read());
    }
    assertNull(cookieLogFileReader.read());
  }
}
