package org.spleen.collection;

import java.util.LinkedList;

import org.spleen.type.CacheObject;

/**
 * Namespace interface.
 * 
 * This interface define namespace usage.
 * 
 * @author vallance
 *
 */
public interface NamespaceInterface extends CacheRemoveable {
	
	/**
	 * Add CacheObject.
	 * 
	 * Insert a new cache object into
	 * the cache storage.
	 * 
	 * @param key			The cache object identifier.
	 * @param cacheObject	The object to store.
	 */
	public void add(String key, CacheObject cacheObject);
	
	/**
	 * Get empty state.
	 * 
	 * Get the empty state of the 
	 * storage.
	 * 
	 * @return	A boolean that represent the empty state of the storage.
	 */
	public boolean isEmpty();
	
	/**
	 * Up a cached object.
	 * 
	 * Reset the time out of a cached
	 * object.
	 * 
	 * @param key	The cached object key.
	 */
	public void up(String key);

	/**
	 * Get the cache object list.
	 * 
	 * Get the cached object list that
	 * reference objects in the insertion
	 * order.
	 * 
	 * @return	The list of objects in insertion order.
	 */
	public LinkedList<String> getCacheObjectOrder();
	
}
