package com.synops.replayreader.common.i18n;

import java.util.ResourceBundle;

public final class I18nUtils {

  public static final String BUNDLE = "i18n.messages";

  private I18nUtils() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static ResourceBundle getBundle() {
    return ResourceBundle.getBundle(BUNDLE);
  }
}
