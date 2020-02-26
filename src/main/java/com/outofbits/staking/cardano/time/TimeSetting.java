package com.outofbits.staking.cardano.time;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Instances of this class encapsulate the important time details for a specific Cardano block
 * chain.
 *
 * @author Kevin Haller
 * @version 1.0.0
 * @since 1.0.0
 */
public final class TimeSetting {

  private final Instant genesisBlockCreation;
  private final long slotsPerEpoch;
  private final Duration slotDuration;

  private TimeSetting(Instant genesisBlockCreation, long slotsPerEpoch, Duration slotDuration) {
    checkArgument(genesisBlockCreation != null, "The given creation time must not be null.");
    checkArgument(slotsPerEpoch > 0,
        "The given number of slots per epoch must be positive and not zero.");
    checkArgument(slotDuration != null && !slotDuration.isNegative() && !slotDuration.isZero(),
        "The given slot duration must not be zero or negative.");
    this.genesisBlockCreation = genesisBlockCreation;
    this.slotsPerEpoch = slotsPerEpoch;
    this.slotDuration = slotDuration;
  }

  /**
   * constructs a {@link TimeSetting} instance with the given details.
   *
   * @param genesisBlockCreation {@link Instant} pointing to the creation time of the genesis
   *                             block.
   * @param slotsPerEpoch        number of slots per epoch for this block chain {@link
   *                             TimeSetting}.
   * @param slotDuration         the duration of a slot for  this block chain {@link TimeSetting}.
   * @return {@link TimeSetting} for the given details.
   */
  public static TimeSetting with(Instant genesisBlockCreation, long slotsPerEpoch,
      Duration slotDuration) {
    return new TimeSetting(genesisBlockCreation, slotsPerEpoch, slotDuration);
  }

  /**
   * gets the creation time of the genesis block.
   *
   * @return the creation time of the genesis block.
   */
  public Instant getGenesisBlockCreationTime() {
    return genesisBlockCreation;
  }

  /**
   * gets the number of slots per epoch for this block chain {@link TimeSetting}.
   *
   * @return the number of slots per epoch for this block chain {@link TimeSetting}.
   */
  public long getSlotsPerEpoch() {
    return slotsPerEpoch;
  }

  /**
   * gets the slot duration for this block chain {@link TimeSetting}.
   *
   * @return the slot duration for this block chain {@link TimeSetting}.
   */
  public Duration getSlotDuration() {
    return slotDuration;
  }

  /**
   * checks whether the given {@code date} ({@link PlainSlotDate}) is valid for this time setting.
   *
   * @param date {@link PlainSlotDate} that shall be checked for validity.
   * @return {@code true}, if the given {@code date} is valid for this time setting, otherwise
   * {@code false}.
   */
  public boolean valid(PlainSlotDate date) {
    return BigInteger.valueOf(slotsPerEpoch).compareTo(date.getSlot()) > 0;
  }

  /**
   * gets the {@link CompleteSlotDate} for the given {@code time}. The given {@code time} must not
   * be before the creation time of the genesis block, otherwise an {@link IllegalArgumentException}
   * will be thrown.
   *
   * @param time {@link Instant} for which the {@link CompleteSlotDate} shall be computed.
   * @return the {@link CompleteSlotDate} for the given {@code time}.
   */
  public CompleteSlotDate getSlotDateFor(Instant time) {
    checkArgument(time != null, "The passed time must not be null.");
    checkArgument(genesisBlockCreation.isBefore(time) || genesisBlockCreation.equals(time),
        "The given time must not be strictly before the genesis block creation time.");
    //TODO: not future safe, due to long value.
    long durationInS = genesisBlockCreation.until(time, ChronoUnit.SECONDS);
    BigInteger totalSlots = BigInteger.valueOf(durationInS / slotDuration.getSeconds());
    BigInteger epoch = totalSlots.divide(BigInteger.valueOf(slotsPerEpoch));
    BigInteger slots = totalSlots.mod(BigInteger.valueOf(slotsPerEpoch));
    return SlotDateFactory.completeInstance(epoch, slots, this);
  }

}
