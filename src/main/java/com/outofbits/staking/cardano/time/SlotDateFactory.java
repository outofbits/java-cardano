package com.outofbits.staking.cardano.time;

import java.math.BigInteger;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A factory for creating instances of {@link PlainSlotDate}.
 *
 * @author Kevin Haller
 * @version 1.0.0
 * @since 1.0.0
 */
public final class SlotDateFactory {

  /**
   * creates a new {@link PlainSlotDate} with the given {@code epoch} and {@code slot} number. Both
   * of those numbers must be positive or zero, or otherwise an {@link IllegalArgumentException}
   * will be thrown.
   *
   * @param epoch positive epoch number of the {@link PlainSlotDate}.
   * @param slot  positive slot number of the {@link PlainSlotDate}.
   * @return {@link PlainSlotDate} for the given {@code epoch} and {@code slot} number.
   * @throws IllegalArgumentException if the {@code epoch} or {@code slot} number was {@code null}
   *                                  or negative.
   */
  public static PlainSlotDate plainInstance(BigInteger epoch, BigInteger slot) {
    checkArgument(epoch != null && epoch.compareTo(BigInteger.ZERO) >= 0,
        "The given epoch number must not be null or negative.");
    checkArgument(slot != null && slot.compareTo(BigInteger.ZERO) >= 0,
        "The given slot number must not be null or negative.");
    return new PlainSlotDateImpl(epoch, slot);
  }

  /**
   * creates a new {@link CompleteSlotDate} with the given {@code epoch} and {@code slot} number as
   * well as the {@code setting} ({@link TimeSetting}). Both of those numbers must be positive or
   * zero, or otherwise an {@link IllegalArgumentException} will be thrown. Moreover, the slot
   * number must be valid for the given {@code setting}.
   *
   * @param epoch   positive epoch number of the {@link CompleteSlotDate}.
   * @param slot    positive slot number of the {@link CompleteSlotDate}.
   * @param setting {@link TimeSetting} of the block chain.
   * @return {@link CompleteSlotDate} for the given details.
   * @throws IllegalArgumentException if the {@code epoch} or {@code slot} number was {@code null}
   *                                  or negative. Moreover, if the given {@code setting} is {@code
   *                                  null}.
   */
  public static CompleteSlotDate completeInstance(BigInteger epoch, BigInteger slot,
      TimeSetting setting) {
    checkArgument(setting != null, "The given time setting must not be null.");
    PlainSlotDate plainSlotDate = plainInstance(epoch, slot);
    checkArgument(setting.valid(plainSlotDate),
        "The given slot date must be valid for the given time setting.");
    return new CompleteSlotDateImpl(plainSlotDate, setting);
  }
}
