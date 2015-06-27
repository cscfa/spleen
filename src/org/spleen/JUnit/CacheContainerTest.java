package org.spleen.JUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;
import org.spleen.collection.NamespaceMultiThread;
import org.spleen.config.SpleenConfigurator;
import org.spleen.type.CacheContainer;
import org.spleen.type.SimpleCacheObject;

public class CacheContainerTest {

	@Test
	public void testCacheContainer() {
		CacheContainer cc = new CacheContainer(new NamespaceMultiThread(1, new SpleenConfigurator()));
		
		assertNotNull(cc);
	}

	@Test
	public void testGetSize() {
		CacheContainer cc = new CacheContainer(new NamespaceMultiThread(1, new SpleenConfigurator()));
		SimpleCacheObject cob = new SimpleCacheObject(120);
		cc.add("ccTest", cob);
		
		assertEquals(cob.getSize(), cc.getSize(), 1);
	}

	@Test
	public void testKeySet() {
		CacheContainer cc = new CacheContainer(new NamespaceMultiThread(1, new SpleenConfigurator()));
		cc.add("ccTest1", new SimpleCacheObject(120));
		cc.add("ccTest2", new SimpleCacheObject(120));
		cc.add("ccTest3", new SimpleCacheObject(120));
		
		ArrayList<String> al1 = new ArrayList<String>();
		al1.add("ccTest1");
		al1.add("ccTest2");
		al1.add("ccTest3");

		ArrayList<String> al2 = new ArrayList<String>();
		Object[] keySet = cc.keySet();
		for (Object string : keySet) {
			al2.add(string.toString());
		}
		assertTrue(al1.containsAll(al2));
	}

	@Test
	public void testGet() {
		CacheContainer cc = new CacheContainer(new NamespaceMultiThread(1, new SpleenConfigurator()));
		SimpleCacheObject cob = new SimpleCacheObject(120);
		cc.add("a", cob);
		
		assertNotNull(cc.get("a"));
		assertTrue(cc.get("a").getClass().toString().equals(SimpleCacheObject.class.toString()));
	}

	@Test
	public void testRemove() {
		CacheContainer cc = new CacheContainer(new NamespaceMultiThread(1, new SpleenConfigurator()));
		SimpleCacheObject cob = new SimpleCacheObject(120);
		cc.add("a", cob);
		cc.remove("a");
		
		assertNull(cc.get("a"));
	}

	@Test
	public void testRemoveFirst() {
		CacheContainer cc = new CacheContainer(new NamespaceMultiThread(1, new SpleenConfigurator()));
		SimpleCacheObject cob = new SimpleCacheObject(120);
		cc.add("a", cob);
		cc.add("b", cob);
		
		cc.removeFirst();
		
		assertNotNull(cc.get("b"));
		assertNull(cc.get("a"));
	}

	@Test
	public void testGetConfig() {
		SpleenConfigurator sc = new SpleenConfigurator();
		CacheContainer cc = new CacheContainer(new NamespaceMultiThread(1, sc));
		
		assertNotNull(cc.getConfig());
		assertEquals(sc, cc.getConfig());
	}

	@Test
	public void testGetCount() {
		CacheContainer cc = new CacheContainer(new NamespaceMultiThread(1, new SpleenConfigurator()));
		SimpleCacheObject cob = new SimpleCacheObject(120);
		cc.add("a", cob);
		cc.add("b", cob);
		cc.add("c", cob);
		
		assertEquals(3, cc.getCount());
		
		cc.remove("a");
		assertEquals(2, cc.getCount());
		
		cc.removeFirst();
		assertEquals(1, cc.getCount());
		
	}

	@Test
	public void testIsEmpty() {
		CacheContainer cc = new CacheContainer(new NamespaceMultiThread(1, new SpleenConfigurator()));
		SimpleCacheObject cob = new SimpleCacheObject(120);
		assertTrue(cc.isEmpty());
		cc.add("a", cob);
		assertFalse(cc.isEmpty());
	}

	@Test
	public void testUp() {
		CacheContainer cc = new CacheContainer(new NamespaceMultiThread(1, new SpleenConfigurator()));
		SimpleCacheObject cob = new SimpleCacheObject(120);
		cc.add("a", cob);
		Long before = cob.getTimeOut();
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			fail();
		}
		
		cc.up("a");
		assertTrue(before < cob.getTimeOut());
	}

	@Test
	public void testGetCacheObjectOrder() {
		CacheContainer cc = new CacheContainer(new NamespaceMultiThread(1, new SpleenConfigurator()));
		assertNotNull(cc.getCacheObjectOrder());
	}

}
