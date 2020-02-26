package com.outofbits.staking.cardano.time;

import java.math.BigInteger;

/**
 * A plain slot date is only determined by its epoch and slot. A plain date is missing further
 * information about the time settings of the Cardano block chain.
 * <p/>
 * A plain date does not know the number of slots in an epoch as well as the duration of a slot in
 * milliseconds. Moreover, the date time of the genesis block is unknown. Hence, a number of methods
 * are not available in the plain date, but can be used if the plain date is transformed into a
 * {@link CompleteSlotDate} by passing the required time settings of the block chain.
 *
 * @author Kevin Haller
 * @version 1.0.0
 * @since 1.0.0
 */
public interface PlainSlotDate {

  /**
   * gets the epoch number of this slot date. The returned epoch must be a positive integer or
   * zero.
   *
   * @return the epoch number of this slot date.
   */
  BigInteger getEpoch();

  /**
   * gets the slot number of this slot date. The returned slot number must be a positive integer or
   * zero.
   *
   * @return the slot number of this slot date.
   */
  BigInteger getSlot();

  /**
   * checks whether this plain slot date is the same as the given {@code otherSlotDate}. Two {@link
   * PlainSlotDate}s are the same, if and only if the epoch number and slot number are the same.
   *
   * @param otherSlotDate {@link PlainSlotDate} that shall be compared with this one.
   * @return {@code true}, if the slot dates are the same, otherwise {@code false}.
   */
  boolean sameAs(PlainSlotDate otherSlotDate);

  /**
   * checks whether this plain date is strictly before the given {@code otherSlotDate}.
   *
   * @param otherSlotDate {@link PlainSlotDate} that shall be compared with this one.
   * @return {@code true}, if this slot date is strictly before {@code otherSlotDate}, otherwise
   * {@code false}.
   */
  boolean before(PlainSlotDate otherSlotDate);

  /**
   * checks whether this plain date is strictly after the given {@code otherSlotDate}.
   *
   * @param otherSlotDate {@link PlainSlotDate} that shall be compared with this one.
   * @return {@code true}, if this slot date is strictly after {@code otherSlotDate}, otherwise
   * {@code false}.
   */
  boolean after(PlainSlotDate otherSlotDate);

}
