package com.munchies.tests;

import static org.junit.Assert.assertEquals;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.Test;

/**
 * Some tests to check the behavior of Duration, Instant and Clock from java.time
 * @author Mehdi Raza
 *
 */
public class DurationTests {

	
	@Test
	public void testDuration() {

		// freeze time
		long millis=Clock.systemUTC().millis();

		Instant fixedInstant = Instant.ofEpochMilli(millis);
		Clock clock = Clock.fixed(fixedInstant, ZoneOffset.UTC);

		assertEquals(clock.instant().plusSeconds(2).toEpochMilli(), millis+2000);
		
	}
	
	@Test
	public void testClock() {
		long millis=Clock.systemDefaultZone().millis();
		long after = Clock.offset(Clock.systemDefaultZone(), Duration.ofSeconds(2)).millis();
		
		assertEquals(millis+2000 , after);
	}
	
	@Test
	public void testDurationUnit() {

		assertEquals(Duration.ofSeconds(1).getSeconds(), Duration.ofMillis(1000).getSeconds());
	}
}
