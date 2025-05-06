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
    var result = internalName;
    var tankName = "";
    var suffix = "";

    try {
      if (internalName.endsWith("_7x7")) {
        tankName = getInstance().getProperties().getProperty(internalName.substring(0, internalName.length() - 4));
        suffix = " 7x7";
      } else {
        tankName = getInstance().getProperties().getProperty(internalName);
      }

      if (StringUtils.isNotEmpty(tankName)) {
        result = tankName + suffix;
      }

      return result;
    } catch (IOException ex) {
      throw new RuntimeException(
          String.format("error mapping internal tank name <%s>", internalName));
    }
  }

  private Properties getProperties() {
    return this.tanksProperties;
  }
}
