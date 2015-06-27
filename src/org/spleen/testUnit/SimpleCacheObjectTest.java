package org.spleen.testUnit;

import static org.junit.Assert.*;

import org.junit.Test;
import org.spleen.config.SpleenConfigurator;
import org.spleen.tool.Sizeof;
import org.spleen.type.SimpleCacheObject;

public class SimpleCacheObjectTest {

	@Test
	public void testSimpleCacheObject() {
		SimpleCacheObject scb = new SimpleCacheObject(12);
		
		assertNotNull(scb);
	}

	@Test
	public void testGetStoredTime() {
		SimpleCacheObject scb = new SimpleCacheObject(12);
		
		assertNull(scb.getStoredTime());
		
		long ctm = System.currentTimeMillis();
		scb.setStoredTime(ctm);
		
		assertEquals((double)ctm, (double)scb.getStoredTime(), 1);
	}

	@Test
	public void testGetTimeOut() {
		SimpleCacheObject scb = new SimpleCacheObject(12);
		
		assertNull(scb.getTimeOut());
		
		long ctm = System.currentTimeMillis();
		scb.setTimeOut(ctm);
		
		assertEquals((double)ctm, (double)scb.getTimeOut(), 1);
	}

	@Test
	public void testGetCacheObject() {
		SimpleCacheObject scb = new SimpleCacheObject(new Integer(12));
		
		assertNotNull(scb.getCacheObject());
		assertTrue(scb.getCacheObject().getClass().toString().equals(Integer.class.toString()));
		assertEquals(12, scb.getCacheObject());
	}

	@Test
	public void testSetCacheObject() {
		SimpleCacheObject scb = new SimpleCacheObject(24);
		scb.setCacheObject(new Integer(12));
		assertNotNull(scb.getCacheObject());
		assertTrue(scb.getCacheObject().getClass().toString().equals(Integer.class.toString()));
		assertEquals(12, scb.getCacheObject());
	}

	@Test
	public void testInit() {
		SimpleCacheObject scb = new SimpleCacheObject(12);
		SpleenConfigurator sc = new SpleenConfigurator();
		scb.init(sc);
		
		assertNotNull(scb.getStoredTime());
		assertNotNull(scb.getTimeOut());
		
		assertTrue(scb.getTimeOut() == (scb.getStoredTime() + sc.getCacheTimeout()));
	}

	@Test
	public void testGetSize() {
		SimpleCacheObject scb = new SimpleCacheObject(null);
		assertNotNull(scb.getSize());
		
		scb.setCacheObject(12);
		assertEquals((double)scb.getSize(), (double)Sizeof.sizeof(12)+16, 1);
		
		scb.setCacheObject(546l);
		assertEquals((double)scb.getSize(), (double)Sizeof.sizeof(546l)+16, 1);
		
		scb.setCacheObject(new Float(12.3));
		assertEquals((double)scb.getSize(), (double)Sizeof.sizeof(new Float(12.3))+16, 1);
		
		scb.setCacheObject(new Double(67844.12));
		assertEquals((double)scb.getSize(), (double)Sizeof.sizeof(new Double(67844.12))+16, 1);
	}

}
