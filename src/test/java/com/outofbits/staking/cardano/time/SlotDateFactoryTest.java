package com.outofbits.staking.cardano.time;

import com.outofbits.staking.cardano.time.PlainSlotDate;
import com.outofbits.staking.cardano.time.SlotDateFactory;
import com.outofbits.staking.cardano.time.TimeSetting;
import java.time.Duration;
import java.time.Instant;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This class tests the {@link SlotDateFactory}.
 */
public class SlotDateFactoryTest {

  private static TimeSetting defaultTimeSetting;

  @BeforeAll
  static void beforeAll() {
    defaultTimeSetting = TimeSetting.with(Instant.parse("2019-12-13T19:13:37.00Z"), 43200L,
        Duration.ofSeconds(2));
  }

  @Test
  void testPlainInstanceNullEpoch_mustThrowIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> {
      SlotDateFactory.plainInstance(null, BigInteger.ZERO);
    });
  }

  @Test
  void testPlainInstanceNullSlot_mustThrowIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> {
      SlotDateFactory.plainInstance(BigInteger.ZERO, null);
    });
  }

  @Test
  void testPlainInstanceNegativeEpoch_mustThrowIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> {
      SlotDateFactory.plainInstance(BigInteger.valueOf(-1), BigInteger.ZERO);
    });
  }

  @Test
  void testPlainInstanceNegativeSlot_mustThrowIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> {
      SlotDateFactory.plainInstance(BigInteger.ZERO, BigInteger.valueOf(-1));
    });
  }

  @Test
  void testPlainInstanceWithCorrectArguments_mustReturnValidInstance() {
    PlainSlotDate date = SlotDateFactory.plainInstance(BigInteger.ZERO, BigInteger.ONE);
    assertEquals(0L, date.getEpoch().longValue());
    assertEquals(1L, date.getSlot().longValue());
  }

  @Test
  void testCompleteInstanceNullEpoch_mustThrowIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> {
      SlotDateFactory.completeInstance(null, BigInteger.ZERO, defaultTimeSetting);
    });
  }

  @Test
  void testCompleteInstanceNullSlot_mustThrowIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> {
      SlotDateFactory.completeInstance(BigInteger.ZERO, null, defaultTimeSetting);
    });
  }

  @Test
  void testCompleteInstanceNegativeEpoch_mustThrowIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> {
      SlotDateFactory.completeInstance(BigInteger.valueOf(-1), BigInteger.ZERO, defaultTimeSetting);
    });
  }

  @Test
  void testCompleteInstanceNegativeSlot_mustThrowIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> {
      SlotDateFactory.completeInstance(BigInteger.ZERO, BigInteger.valueOf(-1), defaultTimeSetting);
    });
  }

  @Test
  void testCompleteInstanceNullTimeSetting_mustThrowIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> {
      SlotDateFactory.completeInstance(BigInteger.ZERO, BigInteger.valueOf(12), null);
    });
  }

  @Test
  void testCompleteInstanceWithInvalidPlainDate_mustThrowIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> {
      SlotDateFactory.completeInstance(BigInteger.ZERO, BigInteger.valueOf(43200), defaultTimeSetting);
    });
  }

}
