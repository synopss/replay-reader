package com.synops.replayreader.update;

import java.time.Duration;
import java.util.Timer;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class VersionChecker {

  private static final Pattern VERSION_PATTERN = Pattern.compile("^v(\\d+)\\.(\\d+)\\.(\\d+)$");
  private static final long SKEW_SECONDS = 240;

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

  public void scheduleVersionCheck(Runnable runnable) {
    var timer = new Timer("Version Update Checker", true);

    var versionCheckTask = new VersionCheckTask(runnable);
    var skew = ThreadLocalRandom.current().nextLong(SKEW_SECONDS) - SKEW_SECONDS / 2;
    timer.scheduleAtFixedRate(versionCheckTask, 0, Duration.ofDays(1).plus(Duration.ofSeconds(skew)).toMillis());
  }
}
