package com.synops.replayreader.update;

import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class VersionChecker {

  private static final Pattern VERSION_PATTERN = Pattern.compile("^v(\\d+)\\.(\\d+)\\.(\\d+)$");

  private static Version createVersion(String versionString) {
    if (!versionString.startsWith("v")) {
      versionString = "v" + versionString;
    }
    var matcher = VERSION_PATTERN.matcher(versionString);

    if (matcher.matches()) {
      return new Version(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)),
          Integer.parseInt(matcher.group(3)));
    }
    return new Version(0, 0, 0);
  }

  public boolean isNewVersionAvailable(String newVersionString, String currentVersionString) {
    if (StringUtils.isBlank(newVersionString)) {
      return false;
    }

    var currentVersion = createVersion(currentVersionString);

    if (currentVersion.isNotARelease()) {
      return false;
    }

    var newVersion = createVersion(newVersionString);
    return newVersion.compareTo(currentVersion) > 0;
  }
}
