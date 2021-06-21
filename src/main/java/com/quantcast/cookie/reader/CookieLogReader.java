package com.quantcast.cookie.reader;

import java.io.IOException;

public interface CookieLogReader {

  String read() throws IOException;
}
