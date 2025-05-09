package com.synops.replayreader.update;

import org.jspecify.annotations.NonNull;

public record Version(int major, int minor, int patch) implements Comparable<Version> {

  @Override
  public int compareTo(Version version) {
    if (major < version.major) {
      return -1;
    } else if (major > version.major) {
      return 1;
    } else {
      if (minor < version.minor) {
        return -1;
      } else if (minor > version.minor) {
        return 1;
      } else {
        return Integer.compare(patch, version.patch);
      }
    }
  }

  @Override
  @NonNull
  public String toString() {
    return major + "." + minor + "." + patch;
  }

  public boolean isNotARelease() {
    return major == 0 && minor == 0 && patch == 0;
  }
}
