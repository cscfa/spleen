package org.spleen.collection;

import org.spleen.config.SpleenConfigurator;
import org.spleen.type.CacheObject;
import org.spleen.type.Sizeable;

/**
 * CacheRemoveable interface.
 * 
 * This interface define the methods
 * used by the cache object remover.
 * 
 * @author vallance
 *
 */
public interface CacheRemoveable extends Sizeable {

	
	/**
	 * Get the keySet.
	 * 
	 * Get the complete keySet of the
	 * cached objects.
	 * 
	 * @return	The complete keySet of the cached objects.
	 */
	public Object[] keySet();
	
	/**
	 * Get a cached object.
	 * 
	 * Get a stored cache object by it
	 * identifier.
	 * 
	 * @param key	The cached object identifier.
	 * @return		The cached object or null if not exist.
	 */
	public CacheObject get(Object key);
	
	/**
	 * Remove a cached object.
	 * 
	 * Remove a stored object from
	 * the cache objects storage.
	 * 
	 * @param key	The cache object identifier.
	 */
	public void remove(Object key);
	
	/**
	 * Remove first object.
	 * 
	 * Remove the first inserted cache
	 * object from the storage.
	 */
	public void removeFirst();
	
	/**
	 * Get config.
	 * 
	 * Return the current configuration.
	 * 
	 * @return	The current SpleenConfigurator.
	 */
	public SpleenConfigurator getConfig();
	
	/**
	 * Get objects count.
	 * 
	 * Get the count of the objects stored.
	 * 
	 * @return	The object count.
	 */
	public int getCount();
}
