package com.quantcast.cookie.reader;

import java.io.IOException;

/** Interface to be implemented by cookie log reader. */
public interface CookieLogReader {

  String read() throws IOException;
}
