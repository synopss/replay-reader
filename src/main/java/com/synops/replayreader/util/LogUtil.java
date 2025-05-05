package com.synops.replayreader.util;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtil {
  private static final PrintStream ps;

  private LogUtil() {
  }

  public static void debug(String msg) {
    ps.printf("%s: %s%n", getTimestamp(), msg);
  }

  private static String getTimestamp() {
    return (new SimpleDateFormat("mm:ss:SSS")).format(new Date());
  }

  static {
    ps = System.out;
  }
}
