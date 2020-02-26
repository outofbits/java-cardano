package com.outofbits.staking.cardano.time;

import com.outofbits.staking.cardano.time.PlainSlotDate;
import com.outofbits.staking.cardano.time.SlotDateFactory;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the default implementation of {@link PlainSlotDate}.
 */
public class PlainSlotDateTest {

  @Test
  void testGetEpoch_mustReturnCorrectEpochNumber() {
    PlainSlotDate date = SlotDateFactory
        .plainInstance(BigInteger.valueOf(42), BigInteger.valueOf(14));
    assertEquals(42L, date.getEpoch().longValue());
  }

  @Test
  void testGetSlot_mustReturnCorrectSlotNumber() {
    PlainSlotDate date = SlotDateFactory
        .plainInstance(BigInteger.valueOf(42), BigInteger.valueOf(14));
    assertEquals(14L, date.getSlot().longValue());
  }

  @Test
  void testSameAsForSameDates_mustReturnTrue() {
    PlainSlotDate thisDate = SlotDateFactory
        .plainInstance(BigInteger.valueOf(42), BigInteger.valueOf(14));
    PlainSlotDate otherDate = SlotDateFactory
        .plainInstance(BigInteger.valueOf(42), BigInteger.valueOf(14));
    assertTrue(thisDate.sameAs(otherDate));
  }

  @Test
  void testSameAsForDifferentDates_mustReturnFalse() {
    PlainSlotDate thisDate = SlotDateFactory
        .plainInstance(BigInteger.valueOf(42), BigInteger.valueOf(14));
    PlainSlotDate otherDate = SlotDateFactory
        .plainInstance(BigInteger.valueOf(42), BigInteger.valueOf(13));
    assertFalse(thisDate.sameAs(otherDate));
  }

  @Test
  void testBeforeForDateBeforeGivenDate_mustReturnTrue() {
    PlainSlotDate thisDate = SlotDateFactory
        .plainInstance(BigInteger.valueOf(42), BigInteger.valueOf(12));
    PlainSlotDate otherDate = SlotDateFactory
        .plainInstance(BigInteger.valueOf(42), BigInteger.valueOf(13));
    assertTrue(thisDate.before(otherDate));
  }

  @Test
  void testBeforeForSameDates_mustReturnFalse() {
    PlainSlotDate thisDate = SlotDateFactory
        .plainInstance(BigInteger.valueOf(42), BigInteger.valueOf(12));
    PlainSlotDate otherDate = SlotDateFactory
        .plainInstance(BigInteger.valueOf(42), BigInteger.valueOf(12));
    assertFalse(thisDate.before(otherDate));
  }

  @Test
  void testAfterForDateAfterGivenDate_mustReturnTrue() {
    PlainSlotDate thisDate = SlotDateFactory
        .plainInstance(BigInteger.valueOf(43), BigInteger.valueOf(12));
    PlainSlotDate otherDate = SlotDateFactory
        .plainInstance(BigInteger.valueOf(42), BigInteger.valueOf(12));
    assertTrue(thisDate.after(otherDate));
  }

  @Test
  void testAfterForSameDates_mustReturnFalse() {
    PlainSlotDate thisDate = SlotDateFactory
        .plainInstance(BigInteger.valueOf(42), BigInteger.valueOf(12));
    PlainSlotDate otherDate = SlotDateFactory
        .plainInstance(BigInteger.valueOf(42), BigInteger.valueOf(12));
    assertFalse(thisDate.after(otherDate));
  }

}
