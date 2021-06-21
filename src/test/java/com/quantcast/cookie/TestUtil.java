package com.quantcast.cookie;

import java.io.File;
import java.util.Objects;

public class TestUtil {

  public static String getResourceFileAbsolutePath(String fileName) {
    ClassLoader classLoader = TestUtil.class.getClassLoader();
    File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
    return file.getAbsolutePath();
  }
}
