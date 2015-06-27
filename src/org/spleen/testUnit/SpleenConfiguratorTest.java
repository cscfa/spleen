package org.spleen.testUnit;

import static org.junit.Assert.*;

import org.junit.Test;
import org.spleen.config.SpleenConfigurator;

public class SpleenConfiguratorTest {

	@Test
	public void testSpleenConfigurator() {
		
		SpleenConfigurator mainConfig = new SpleenConfigurator();
		
		assertNotNull("mainConfig is null", mainConfig);
		
	}

	@Test
	public void testSpleenConfiguratorLongLongDoubleInt() {
		
		SpleenConfigurator mainConfig = new SpleenConfigurator(100l, 50l, 512.10d, 20);
		
		assertEquals(100l, mainConfig.getRemoverSheduleTime());
		assertEquals(50l, mainConfig.getCacheTimeout());
		assertEquals(512.10d, mainConfig.getMaxSize(), 0.01);
		assertEquals(20, mainConfig.getMaxCount());

	}

}
