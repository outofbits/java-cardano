package com.outofbits.staking.cardano.time;

import java.math.BigInteger;

/**
 * A simple implementation of {@link PlainSlotDate}.
 *
 * @author Kevin Haller
 * @version 1.0.0
 * @since 1.0.0
 */
class PlainSlotDateImpl implements PlainSlotDate {

  private final BigInteger epoch;
  private final BigInteger slot;

  PlainSlotDateImpl(BigInteger epoch, BigInteger slot) {
    this.epoch = epoch;
    this.slot = slot;
  }

  public BigInteger getEpoch() {
    return epoch;
  }

  public BigInteger getSlot() {
    return slot;
  }

  public boolean sameAs(PlainSlotDate otherSlotDate) {
    return epoch.equals(otherSlotDate.getEpoch()) && slot.equals(otherSlotDate.getSlot());
  }

  public boolean before(PlainSlotDate otherSlotDate) {
    if (epoch.compareTo(otherSlotDate.getEpoch()) < 0) {
      return true;
    } else if (epoch.compareTo(otherSlotDate.getEpoch()) == 0) {
      return slot.compareTo(otherSlotDate.getSlot()) < 0;
    }
    return false;
  }

  public boolean after(PlainSlotDate otherSlotDate) {
    if (epoch.compareTo(otherSlotDate.getEpoch()) > 0) {
      return true;
    } else if (epoch.compareTo(otherSlotDate.getEpoch()) == 0) {
      return slot.compareTo(otherSlotDate.getSlot()) > 0;
    }
    return false;
  }
}
