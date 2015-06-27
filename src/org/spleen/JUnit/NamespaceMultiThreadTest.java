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
import org.spleen.type.CacheObject;
import org.spleen.type.SimpleCacheObject;

public class NamespaceMultiThreadTest {

	@Test
	public void testNamespaceMap() {
		
		SpleenConfigurator sc = new SpleenConfigurator();
		NamespaceMultiThread nm = new NamespaceMultiThread(10, sc);
		
		assertNotNull(nm);
	}

	@Test
	public void testAdd() {

		SpleenConfigurator sc = new SpleenConfigurator();
		NamespaceMultiThread nm = new NamespaceMultiThread(10, sc);
		
		nm.add("nmTest", new SimpleCacheObject(new Integer(120)));
		
		assertNotNull(nm.get("nmTest"));
		assertTrue(nm.get("nmTest").getClass().toString().equals(SimpleCacheObject.class.toString()));
		assertTrue(nm.get("nmTest").getCacheObject().getClass().toString().equals(Integer.class.toString()));
		assertEquals(nm.get("nmTest").getCacheObject(), 120);
	}

	@Test
	public void testRemove() {
		SpleenConfigurator sc = new SpleenConfigurator();
		NamespaceMultiThread nm = new NamespaceMultiThread(10, sc);
		
		nm.add("nmTest", new SimpleCacheObject(120));
		nm.remove("nmTest");
		
		assertNull(nm.get("nmTest"));
	}

	@Test
	public void testRemoveFirst() {
		SpleenConfigurator sc = new SpleenConfigurator();
		NamespaceMultiThread nm = new NamespaceMultiThread(10, sc);

		nm.add("first", new SimpleCacheObject(10));
		nm.add("second", new SimpleCacheObject(20));
		nm.add("third", new SimpleCacheObject(30));
		
		assertEquals(nm.getCount(), 3);
		
		nm.removeFirst();
		assertEquals(nm.getCount(), 2);
		assertNull(nm.get("first"));

		nm.removeFirst();
		assertEquals(nm.getCount(), 1);
		assertNull(nm.get("second"));
		assertNotNull(nm.get("third"));
		assertEquals(nm.getCacheObjectOrder().size(), 1);
		
	}

	@Test
	public void testGetConfig() {
		SpleenConfigurator sc = new SpleenConfigurator();
		NamespaceMultiThread nm = new NamespaceMultiThread(10, sc);
		
		assertNotNull(nm.getConfig());
		assertEquals(nm.getConfig(), sc);
	}

	@Test
	public void testIsEmpty() {
		SpleenConfigurator sc = new SpleenConfigurator();
		NamespaceMultiThread nm = new NamespaceMultiThread(10, sc);
		assertTrue(nm.isEmpty());
		nm.add("nmTest", new SimpleCacheObject(12));
		assertFalse(nm.isEmpty());
		nm.remove("nmTest");
		assertTrue(nm.isEmpty());
	}

	@Test
	public void testGetSize() {
		SpleenConfigurator sc = new SpleenConfigurator();
		NamespaceMultiThread nm = new NamespaceMultiThread(10, sc);

		
		CacheObject cob = new SimpleCacheObject(12);
		nm.add("nmTest", cob);
		
		assertEquals(nm.getSize(), cob.getSize(), 1);
	}

	@Test
	public void testKeySet() {
		SpleenConfigurator sc = new SpleenConfigurator();
		NamespaceMultiThread nm = new NamespaceMultiThread(10, sc);

		nm.add("nmTest1", new SimpleCacheObject(null));
		nm.add("nmTest2", new SimpleCacheObject(null));
		nm.add("nmTest3", new SimpleCacheObject(null));
		nm.add("nmTest4", new SimpleCacheObject(null));
		
		Object[] keySet = nm.keySet();
		ArrayList<String> testKS = new ArrayList<String>();
		testKS.add("nmTest1");
		testKS.add("nmTest2");
		testKS.add("nmTest3");
		testKS.add("nmTest4");
		
		ArrayList<String> testKSC = new ArrayList<String>();
		for (Object string : keySet) {
			testKSC.add(string.toString());
		}

		assertTrue(testKS.containsAll(testKSC));
	}

	@Test
	public void testUp() {
		SpleenConfigurator sc = new SpleenConfigurator();
		NamespaceMultiThread nm = new NamespaceMultiThread(10, sc);

		nm.add("nmTest1", new SimpleCacheObject(null));
		
		Long before = nm.get("nmTest1").getTimeOut();

		try{ Thread.sleep(10); }catch(InterruptedException e){ fail(); };
		
		nm.up("nmTest1");
		
		assertTrue((before < nm.get("nmTest1").getTimeOut()));
	}

}
