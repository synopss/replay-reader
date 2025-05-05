package com.synops.replayreader.model;

public interface VehicleStats {
  String getVehicleId();

  int getXp();

  int getKills();

  int getCredits();

  int getDamageDealt();

  int getSniperDamageDealt();

  int getTdamageDealt();

  int getDamageAssistedRadio();

  int getDamageAssistedTrack();

  int getDamageBlockedByArmor();

  int getDamageReceivedFromInvisibles();

  int getSpotted();

  int getCapturePoints();

  int getShots();

  int getPiercings();

  int getDamaged();

  int getDamageReceived();

  int getDirectHitsReceived();

  int getMileage();

  String getKillerID();

  int getHealth();

  String getTeam();

  int getLifeTime();

  int getDirectHits();

  int getTKills();

  int getDroppedCapturePoints();

  int getFlagCapture();

  double getStunDuration();

  int getDeathCount();

  int getStunned();

  int getDamageAssistedStun();

  int getStunNum();
}
