package com.outofbits.staking.cardano;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SuiteDisplayName("JUnit Tests for the time package.")
@SelectPackages("com.outofbits.staking.cardano.time")
public class TimeTestSuite {

}
