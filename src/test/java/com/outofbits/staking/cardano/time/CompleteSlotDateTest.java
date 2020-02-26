package com.outofbits.staking.cardano.time;

import com.outofbits.staking.cardano.time.CompleteSlotDate;
import com.outofbits.staking.cardano.time.SlotDateFactory;
import com.outofbits.staking.cardano.time.TimeSetting;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This class tests {@link CompleteSlotDate}.
 */
public class CompleteSlotDateTest {

  private static TimeSetting defaultTimeSetting;

  @BeforeAll
  static void beforeAll() {
    defaultTimeSetting = TimeSetting.with(Instant.parse("2019-12-13T19:13:37.00Z"), 43200L,
        Duration.ofSeconds(2));
  }

  @Test
  void testGetTimeSettings_mustReturnCorrectTimeSetting() {
    Instant creationGenesisBlockTime = Instant.now();
    TimeSetting setting = TimeSetting.with(creationGenesisBlockTime, 43200L, Duration.ofSeconds(2));
    CompleteSlotDate date = SlotDateFactory
        .completeInstance(BigInteger.ZERO, BigInteger.ONE, setting);
    assertNotNull(date.getTimeSetting());
    assertEquals(43200L, date.getTimeSetting().getSlotsPerEpoch());
    assertEquals(2, date.getTimeSetting().getSlotDuration().getSeconds());
    assertEquals(creationGenesisBlockTime, date.getTimeSetting().getGenesisBlockCreationTime());
  }

  @Test
  void testGetStartTime_mustReturnCorrectTime() {
    CompleteSlotDate date = SlotDateFactory
        .completeInstance(BigInteger.valueOf(17), BigInteger.valueOf(10653), defaultTimeSetting);
    Instant expectedStart = Instant.parse("2019-12-31T01:08:43.00Z");
    assertNotNull(date.getStartTime());
    assertEquals(expectedStart, date.getStartTime());
  }

  @Test
  void testGetEndTime_mustReturnCorrectTime() {
    CompleteSlotDate date = SlotDateFactory
        .completeInstance(BigInteger.valueOf(17), BigInteger.valueOf(10653), defaultTimeSetting);
    Instant expectedStart = Instant.parse("2019-12-31T01:08:45.00Z");
    assertNotNull(date.getEndTime());
    assertEquals(expectedStart, date.getEndTime());
  }

  @Test
  void testDifferenceBetweenABeforeB_mustReturnPositiveDifference() {
    CompleteSlotDate a = SlotDateFactory
        .completeInstance(BigInteger.valueOf(17), BigInteger.valueOf(1200), defaultTimeSetting);
    CompleteSlotDate b = SlotDateFactory
        .completeInstance(BigInteger.valueOf(16), BigInteger.valueOf(35600), defaultTimeSetting);
    assertEquals(8800L, a.difference(b).longValue());
  }

  @Test
  void testDifferenceBetweenSameDates_mustReturnZero() {
    CompleteSlotDate a = SlotDateFactory
        .completeInstance(BigInteger.valueOf(4), BigInteger.valueOf(0), defaultTimeSetting);
    CompleteSlotDate b = SlotDateFactory
        .completeInstance(BigInteger.valueOf(4), BigInteger.valueOf(0), defaultTimeSetting);
    assertEquals(0L, a.difference(b).longValue());
  }

  @Test
  void testDifferenceBetweenAAfterB_mustReturnNegativeDifference() {
    CompleteSlotDate a = SlotDateFactory
        .completeInstance(BigInteger.valueOf(8), BigInteger.valueOf(40100), defaultTimeSetting);
    CompleteSlotDate b = SlotDateFactory
        .completeInstance(BigInteger.valueOf(10), BigInteger.valueOf(35600), defaultTimeSetting);
    assertEquals(-81900L, a.difference(b).longValue());
  }

}
