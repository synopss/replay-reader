package com.synops.replayreader.vehicle.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class VehicleStatsImpl implements VehicleStats {

  private String vehicleId;
  private int xp;
  private int kills;
  private int credits;
  private int damageDealt;
  private int sniperDamageDealt;
  private int tdamageDealt;
  private int damageAssistedRadio;
  private int damageAssistedTrack;
  private int damageBlockedByArmor;
  private int damageReceivedFromInvisibles;
  private int spotted;
  private int capturePoints;
  private int shots;
  private int piercings;
  private int damaged;
  private int damageReceived;
  private int directHitsReceived;
  private int mileage;
  private String killerID;
  private int health;
  private String team;
  private int lifeTime;
  private int directHits;
  private int droppedCapturePoints;
  private int flagCapture;
  private double stunDuration;
  private int deathCount;
  private int stunned;
  private int damageAssistedStun;
  private int stunNum;
  private int tkills;

  public int getDroppedCapturePoints() {
    return this.droppedCapturePoints;
  }

  public void setDroppedCapturePoints(int droppedCapturePoints) {
    this.droppedCapturePoints = droppedCapturePoints;
  }

  public int getFlagCapture() {
    return this.flagCapture;
  }

  public void setFlagCapture(int flagCapture) {
    this.flagCapture = flagCapture;
  }

  public double getStunDuration() {
    return this.stunDuration;
  }

  public void setStunDuration(double stunDuration) {
    this.stunDuration = stunDuration;
  }

  public int getDeathCount() {
    return this.deathCount;
  }

  public void setDeathCount(int deathCount) {
    this.deathCount = deathCount;
  }

  public int getStunned() {
    return this.stunned;
  }

  public void setStunned(int stunned) {
    this.stunned = stunned;
  }

  public int getDamageAssistedStun() {
    return this.damageAssistedStun;
  }

  public void setDamageAssistedStun(int damageAssistedStun) {
    this.damageAssistedStun = damageAssistedStun;
  }

  public int getStunNum() {
    return this.stunNum;
  }

  public void setStunNum(int stunNum) {
    this.stunNum = stunNum;
  }

  public int getTKills() {
    return this.tkills;
  }

  public void setTKills(int tkills) {
    this.tkills = tkills;
  }

  public String getVehicleId() {
    return this.vehicleId;
  }

  public void setVehicleId(String vehicleId) {
    this.vehicleId = vehicleId;
  }

  public int getXp() {
    return this.xp;
  }

  public void setXp(int xp) {
    this.xp = xp;
  }

  public int getKills() {
    return this.kills;
  }

  public void setKills(int kills) {
    this.kills = kills;
  }

  public int getCredits() {
    return this.credits;
  }

  public void setCredits(int credits) {
    this.credits = credits;
  }

  public int getDamageDealt() {
    return this.damageDealt;
  }

  public void setDamageDealt(int damageDealt) {
    this.damageDealt = damageDealt;
  }

  public int getSniperDamageDealt() {
    return this.sniperDamageDealt;
  }

  public void setSniperDamageDealt(int sniperDamageDealt) {
    this.sniperDamageDealt = sniperDamageDealt;
  }

  public int getTdamageDealt() {
    return this.tdamageDealt;
  }

  public void setTdamageDealt(int tdamageDealt) {
    this.tdamageDealt = tdamageDealt;
  }

  public int getDamageAssistedRadio() {
    return this.damageAssistedRadio;
  }

  public void setDamageAssistedRadio(int damageAssistedRadio) {
    this.damageAssistedRadio = damageAssistedRadio;
  }

  public int getDamageAssistedTrack() {
    return this.damageAssistedTrack;
  }

  public void setDamageAssistedTrack(int damageAssistedTrack) {
    this.damageAssistedTrack = damageAssistedTrack;
  }

  public int getDamageBlockedByArmor() {
    return this.damageBlockedByArmor;
  }

  public void setDamageBlockedByArmor(int damageBlockedByArmor) {
    this.damageBlockedByArmor = damageBlockedByArmor;
  }

  public int getDamageReceivedFromInvisibles() {
    return this.damageReceivedFromInvisibles;
  }

  public void setDamageReceivedFromInvisibles(int damageReceivedFromInvisibles) {
    this.damageReceivedFromInvisibles = damageReceivedFromInvisibles;
  }

  public int getSpotted() {
    return this.spotted;
  }

  public void setSpotted(int spotted) {
    this.spotted = spotted;
  }

  public int getCapturePoints() {
    return this.capturePoints;
  }

  public void setCapturePoints(int capturePoints) {
    this.capturePoints = capturePoints;
  }

  public int getShots() {
    return this.shots;
  }

  public void setShots(int shots) {
    this.shots = shots;
  }

  public int getPiercings() {
    return this.piercings;
  }

  public void setPiercings(int piercings) {
    this.piercings = piercings;
  }

  public int getDamaged() {
    return this.damaged;
  }

  public void setDamaged(int damaged) {
    this.damaged = damaged;
  }

  public int getDamageReceived() {
    return this.damageReceived;
  }

  public void setDamageReceived(int damageReceived) {
    this.damageReceived = damageReceived;
  }

  public int getDirectHitsReceived() {
    return this.directHitsReceived;
  }

  public void setDirectHitsReceived(int directHitsReceived) {
    this.directHitsReceived = directHitsReceived;
  }

  public int getMileage() {
    return this.mileage;
  }

  public void setMileage(int mileage) {
    this.mileage = mileage;
  }

  public String getKillerID() {
    return this.killerID;
  }

  public void setKillerID(String killerID) {
    this.killerID = killerID;
  }

  public int getHealth() {
    return this.health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public String getTeam() {
    return this.team;
  }

  public void setTeam(String team) {
    this.team = team;
  }

  public int getLifeTime() {
    return this.lifeTime;
  }

  public void setLifeTime(int lifeTime) {
    this.lifeTime = lifeTime;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  public int getDirectHits() {
    return this.directHits;
  }

  public void setDirectHits(int directHits) {
    this.directHits = directHits;
  }
}
