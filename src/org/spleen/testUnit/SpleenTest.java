package org.spleen.testUnit;

import static org.junit.Assert.*;

import org.junit.Test;
import org.spleen.Spleen;
import org.spleen.collection.NamespaceMap;
import org.spleen.collection.NamespaceMultiThread;
import org.spleen.config.SpleenConfigurator;
import org.spleen.type.CacheObject;
import org.spleen.type.SimpleCacheObject;

public class SpleenTest {

	@Test
	public void testCreateNamespaceStringSpleenConfigurator() {

		Spleen mainSpleen = new Spleen();
		mainSpleen.createNamespace("spleenTest", this.getConfig());
		
		assertNotNull(mainSpleen);
		assertNotNull(mainSpleen.get("spleenTest"));
		
		assertTrue(mainSpleen.get("spleenTest").getClass().toString().equals(NamespaceMap.class.toString()));
		
	}

	@Test
	public void testCreateNamespaceIntStringSpleenConfigurator() {

		Spleen mainSpleen = new Spleen();
		mainSpleen.createNamespace(10, "spleenTest", this.getConfig());
		
		assertNotNull(mainSpleen);
		assertNotNull(mainSpleen.get("spleenTest"));
		
		assertTrue(mainSpleen.get("spleenTest").getClass().toString().equals(NamespaceMultiThread.class.toString()));
	}

	@Test
	public void testRemoveNamespace() {

		Spleen mainSpleen = new Spleen();
		mainSpleen.createNamespace("spleenTest", this.getConfig());
		mainSpleen.removeNamespace("spleenTest");
		
		assertNull(mainSpleen.get("spleenTest"));
		
		mainSpleen.createNamespace(10, "spleenTest", this.getConfig());
		mainSpleen.removeNamespace("spleenTest");
		
		assertNull(mainSpleen.get("spleenTest"));
	}

	@Test
	public void testGetStringString() {

		Spleen mainSpleen = new Spleen();
		mainSpleen.createNamespace("spleenTest", this.getConfig());
		
		CacheObject cob = new SimpleCacheObject(new Integer(152));
		mainSpleen.put("spleenTest", "objectTest", cob);

		assertNotNull(mainSpleen.get("spleenTest", "objectTest"));
		assertTrue(mainSpleen.get("spleenTest", "objectTest").getClass().toString().equals(SimpleCacheObject.class.toString()));
		assertTrue(mainSpleen.get("spleenTest", "objectTest").getCacheObject().getClass().toString().equals(Integer.class.toString()));

		mainSpleen.removeNamespace("spleenTest");
		mainSpleen.createNamespace(10, "spleenTest", this.getConfig());
		
		mainSpleen.put("spleenTest", "objectTest", cob);
		
		assertNotNull(mainSpleen.get("spleenTest", "objectTest"));
		assertTrue(mainSpleen.get("spleenTest", "objectTest").getClass().toString().equals(SimpleCacheObject.class.toString()));
		assertTrue(mainSpleen.get("spleenTest", "objectTest").getCacheObject().getClass().toString().equals(Integer.class.toString()));
	}

	@Test
	public void testGetSize() {

		Spleen mainSpleen = new Spleen();
		mainSpleen.createNamespace("spleenTest", this.getConfig());
		
		CacheObject cob = new SimpleCacheObject(new Integer(152));
		mainSpleen.put("spleenTest", "objectTest", cob);
		
		assertEquals(mainSpleen.getSize(), cob.getSize(), 1);
	}
	
	public SpleenConfigurator getConfig(){
		return new SpleenConfigurator();
	}

}
