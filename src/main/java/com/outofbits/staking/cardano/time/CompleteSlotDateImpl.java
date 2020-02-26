package com.outofbits.staking.cardano.time;

import java.math.BigInteger;
import java.time.Instant;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A simple implementation of {@link CompleteSlotDate}.
 *
 * @author Kevin Haller
 * @version 1.0.0
 * @since 1.0.0
 */
class CompleteSlotDateImpl implements CompleteSlotDate {

  private final PlainSlotDate plainSlotDate;
  private final TimeSetting setting;

  CompleteSlotDateImpl(PlainSlotDate plainSlotDate, TimeSetting setting) {
    this.plainSlotDate = plainSlotDate;
    this.setting = setting;
  }

  @Override
  public TimeSetting getTimeSetting() {
    return setting;
  }

  @Override
  public Instant getStartTime() {
    BigInteger slots = this.plainSlotDate.getSlot().add(
        BigInteger.valueOf(this.setting.getSlotsPerEpoch())
            .multiply(this.plainSlotDate.getEpoch()));
    // TODO: not safe for big numbers
    return setting.getGenesisBlockCreationTime()
        .plus(setting.getSlotDuration().multipliedBy(slots.longValue()));
  }

  @Override
  public Instant getEndTime() {
    return getStartTime().plus(setting.getSlotDuration());
  }

  @Override
  public BigInteger difference(PlainSlotDate otherSlotDate) {
    checkArgument(
        this.setting.valid(otherSlotDate),
        "The given other slot date must be valid for the time setting of this complete slot date.");
    BigInteger a = this.plainSlotDate.getSlot().add(
        BigInteger.valueOf(this.setting.getSlotsPerEpoch())
            .multiply(this.plainSlotDate.getEpoch()));
    BigInteger b = otherSlotDate.getSlot().add(
        BigInteger.valueOf(this.setting.getSlotsPerEpoch())
            .multiply(otherSlotDate.getEpoch()));
    return a.subtract(b);
  }

  @Override
  public BigInteger getEpoch() {
    return plainSlotDate.getEpoch();
  }

  @Override
  public BigInteger getSlot() {
    return plainSlotDate.getSlot();
  }

  @Override
  public boolean sameAs(PlainSlotDate otherSlotDate) {
    return plainSlotDate.sameAs(otherSlotDate);
  }

  @Override
  public boolean before(PlainSlotDate otherSlotDate) {
    return plainSlotDate.before(otherSlotDate);
  }

  @Override
  public boolean after(PlainSlotDate otherSlotDate) {
    return plainSlotDate.after(otherSlotDate);
  }
}
