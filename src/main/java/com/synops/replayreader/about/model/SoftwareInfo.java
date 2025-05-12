package com.synops.replayreader.about.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SoftwareInfo {

  private final StringProperty software;
  private final StringProperty softwareUrl;
  private final StringProperty version;
  private final StringProperty license;
  private final StringProperty licenseUrl;

  public SoftwareInfo(String software, String softwareUrl, String version, String license,
      String licenseUrl) {
    this.software = new SimpleStringProperty(software);
    this.softwareUrl = new SimpleStringProperty(softwareUrl);
    this.version = new SimpleStringProperty(version);
    this.license = new SimpleStringProperty(license);
    this.licenseUrl = new SimpleStringProperty(licenseUrl);
  }

  public StringProperty softwareProperty() {
    return software;
  }

  public StringProperty softwareUrlProperty() {
    return softwareUrl;
  }

  public StringProperty versionProperty() {
    return version;
  }

  public StringProperty licenseProperty() {
    return license;
  }

  public StringProperty licenseUrlProperty() {
    return licenseUrl;
  }

  public String getSoftware() {
    return software.get();
  }

  public void setSoftware(String software) {
    this.software.set(software);
  }

  public String getSoftwareUrl() {
    return softwareUrl.get();
  }

  public void setSoftwareUrl(String softwareUrl) {
    this.softwareUrl.set(softwareUrl);
  }

  public String getVersion() {
    return version.get();
  }

  public void setVersion(String version) {
    this.version.set(version);
  }

  public String getLicense() {
    return license.get();
  }

  public void setLicense(String license) {
    this.license.set(license);
  }

  public String getLicenseUrl() {
    return licenseUrl.get();
  }

  public void setLicenseUrl(String licenseUrl) {
    this.licenseUrl.set(licenseUrl);
  }
}
