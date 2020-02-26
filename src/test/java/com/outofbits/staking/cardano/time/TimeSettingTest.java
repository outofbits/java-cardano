package com.outofbits.staking.cardano.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.outofbits.staking.cardano.time.CompleteSlotDate;
import com.outofbits.staking.cardano.time.PlainSlotDate;
import com.outofbits.staking.cardano.time.SlotDateFactory;
import com.outofbits.staking.cardano.time.TimeSetting;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class tests the {@link TimeSetting}.
 */
public class TimeSettingTest {

  private TimeSetting defaultTimeSetting;

  @BeforeEach
  void setUp() {
    defaultTimeSetting = TimeSetting.with(Instant.parse("2019-12-13T19:13:37.00Z"), 43200L,
        Duration.ofSeconds(2));
  }

  @Test
  void testGetterMethods_mustReturnCorrectDetails() {
    assertEquals(43200L, defaultTimeSetting.getSlotsPerEpoch());
    assertEquals(2, defaultTimeSetting.getSlotDuration().getSeconds());
    assertEquals(Instant.parse("2019-12-13T19:13:37.00Z"),
        defaultTimeSetting.getGenesisBlockCreationTime());
  }

  @Test
  void testValidMethodForValidDate_mustReturnTrue() {
    PlainSlotDate date = SlotDateFactory
        .plainInstance(BigInteger.valueOf(4), BigInteger.valueOf(43199));
    assertTrue(defaultTimeSetting.valid(date));
  }

  @Test
  void testValidMethodForInvalidDate_mustReturnFalse() {
    PlainSlotDate date = SlotDateFactory
        .plainInstance(BigInteger.valueOf(4), BigInteger.valueOf(43200));
    assertFalse(defaultTimeSetting.valid(date));
  }

  @Test
  void testGetSlotDateFor_mustReturnCorrectSlotDate() {
    CompleteSlotDate date = defaultTimeSetting
        .getSlotDateFor(Instant.parse("2020-01-14T15:51:37Z"));
    // are the time settings correct?
    assertNotNull(date.getTimeSetting());
    assertEquals(43200L, date.getTimeSetting().getSlotsPerEpoch());
    assertEquals(2, date.getTimeSetting().getSlotDuration().getSeconds());
    assertEquals(Instant.parse("2019-12-13T19:13:37.00Z"),
        date.getTimeSetting().getGenesisBlockCreationTime());
    // check if slot date is correct?
    assertEquals(31L, date.getEpoch().longValue());
    assertEquals(37140L, date.getSlot().longValue());
  }

  @Test
  void testGetSlotDateForGenesis_mustReturnZeroEpochZeroSlotDate() {
    CompleteSlotDate date = defaultTimeSetting
        .getSlotDateFor(Instant.parse("2019-12-13T19:13:37.00Z"));
    // are the time settings correct?
    assertNotNull(date.getTimeSetting());
    assertEquals(43200L, date.getTimeSetting().getSlotsPerEpoch());
    assertEquals(2, date.getTimeSetting().getSlotDuration().getSeconds());
    assertEquals(Instant.parse("2019-12-13T19:13:37.00Z"),
        date.getTimeSetting().getGenesisBlockCreationTime());
    // check if slot date is correct?
    assertEquals(0L, date.getEpoch().longValue());
    assertEquals(0L, date.getSlot().longValue());
  }

  @Test
  void testGetSlotDate_mustThrowIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> {
      defaultTimeSetting
          .getSlotDateFor(Instant.parse("2019-12-13T19:13:35.00Z"));
    });
  }

}
