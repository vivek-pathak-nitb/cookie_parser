package com.quantcast.cookie.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/** Log file based cookie reader. */
public class CookieLogFileReader implements CookieLogReader {

  private final BufferedReader bufferedReader;

  public CookieLogFileReader(String path) throws IOException {
    File file = new File(path);
    if (!file.exists()) {
      throw new FileNotFoundException("File doesn't exist");
    }
    bufferedReader = new BufferedReader(new FileReader(file));
  }

  @Override
  public String read() throws IOException {
    String line = bufferedReader.readLine();
    if (line == null) {
      bufferedReader.close();
    }
    return line;
  }
}
