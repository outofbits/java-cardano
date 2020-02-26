package com.outofbits.staking.cardano.time;

import java.math.BigInteger;
import java.time.Instant;
import java.util.function.Function;

/**
 * A complete slot data extends the {@link PlainSlotDate} that only includes the epoch and slot
 * number with the {@link TimeSetting} of the block chain. Consequently, a number of new methods are
 * available.
 *
 * @author Kevin Haller
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CompleteSlotDate extends PlainSlotDate {

  /**
   * gets the {@link TimeSetting} for this {@link CompleteSlotDate}.
   *
   * @return the {@link TimeSetting} for this {@link CompleteSlotDate}.
   */
  TimeSetting getTimeSetting();

  /**
   * gets the start time of this slot date as an {@link Instant}.
   *
   * @return the start time of this slot date as an {@link Instant}.
   */
  Instant getStartTime();

  /**
   * gets the end time of this slot date as an {@link Instant}.
   *
   * @return the end time of this slot date as an {@link Instant}.
   */
  Instant getEndTime();

  /**
   * gets the number of slots that are between this {@link CompleteSlotDate} and the given {@code
   * otherSlotDate}. Should this date be strictly after {@code otherSlotDate}, then the returned
   * difference will be negative.
   *
   * @param otherSlotDate {@link PlainSlotDate} to which the difference shall be computed.
   * @return the number of slots that are between this {@link CompleteSlotDate} and the given {@code
   * otherSlotDate}.
   * @throws IllegalArgumentException if the given {@code otherSlotDate} is not valid for the time
   *                                  settings of this {@link CompleteSlotDate}.
   */
  BigInteger difference(PlainSlotDate otherSlotDate);

  /**
   * transforms the given {@code date} ({@link PlainSlotDate}) into a {@link CompleteSlotDate} given
   * the {@code setting} ({@link TimeSetting}).
   *
   * @param date    {@link PlainSlotDate} that shall be transformed into a {@link
   *                CompleteSlotDate}.
   * @param setting {@link TimeSetting} of the block chain for which the {@link PlainSlotDate} shall
   *                be transformed.
   * @return {@link CompleteSlotDate}, which is a result of the given {@link PlainSlotDate}.
   * @throws IllegalArgumentException if the given date plus setting is not valid.
   */
  static CompleteSlotDate transform(PlainSlotDate date, TimeSetting setting) {
    return SlotDateFactory.completeInstance(date.getEpoch(), date.getSlot(), setting);
  }
}
