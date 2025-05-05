package com.synops.replayreader.vehicle.util;

import io.micrometer.common.util.StringUtils;
import java.io.IOException;
import java.util.Properties;

public class TanksUtil {

  private static TanksUtil instance = null;
  private final Properties tanksProperties = new Properties();

  private TanksUtil() throws IOException {
    this.tanksProperties.load(
        TanksUtil.class.getClassLoader().getResourceAsStream("tanks.properties"));
  }

  private static TanksUtil getInstance() throws IOException {
    if (instance == null) {
      instance = new TanksUtil();
    }

    return instance;
  }

  public static String getTankName(String internalName) {
    String result = internalName;

    try {
      String property = getInstance().getProperties().getProperty(internalName);
      if (StringUtils.isNotEmpty(property)) {
        result = property;
      }

      return result;
    } catch (IOException var3) {
      throw new RuntimeException(
          String.format("error mapping internal tank name <%s>", internalName));
    }
  }

  private Properties getProperties() {
    return this.tanksProperties;
  }
}
