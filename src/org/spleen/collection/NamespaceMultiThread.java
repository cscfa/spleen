/**
 * 
 */
package org.spleen.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import org.spleen.config.SpleenConfigurator;
import org.spleen.threading.CacheOutRemover;
import org.spleen.type.CacheContainer;
import org.spleen.type.CacheObject;

/**
 * @author vallance
 *
 */
public class NamespaceMultiThread implements NamespaceInterface {

	protected SpleenConfigurator config;
	protected ArrayList<CacheContainer> cacheContainers;
	protected LinkedList<Integer> cacheObjectOrder;
	private int containerCount = 1;
	private double size = 0;
	private int containerTarget = 0;
	
	/**
	 * 
	 */
	public NamespaceMultiThread(int containerCount, SpleenConfigurator config) {
		this.containerCount = containerCount;
		this.config = config;
		this.cacheContainers = new ArrayList<CacheContainer>();
		this.cacheObjectOrder = new LinkedList<Integer>();
		
		for(int i = 0;i < containerCount;i++){
			this.cacheContainers.add(new CacheContainer(this));
		}
	}

	/**
	 * @see org.spleen.collection.CacheRemoveable#keySet()
	 */
	@Override
	public Object[] keySet() {
		Object[] keySet = new Object[0];
		for (CacheContainer cacheContainer : this.cacheContainers) {
			int cacheLen = cacheContainer.keySet().length;
			int keyLen = keySet.length;
			
			if(keyLen > 0){
				Object[] c= new Object[cacheLen+keyLen];
				System.arraycopy(keySet, 0, c, 0, keyLen);
				System.arraycopy(cacheContainer.keySet(), 0, c, keyLen, cacheLen);
				keySet = c;
			}else{
				keySet = cacheContainer.keySet();
			}
		}
		return keySet;
	}

	/**
	 * @see org.spleen.collection.CacheRemoveable#get(java.lang.Object)
	 */
	@Override
	public CacheObject get(Object key) {
		for (CacheContainer cacheContainer : this.cacheContainers) {
			if(cacheContainer.get(key) != null){
				return cacheContainer.get(key);
			}else{
				continue;
			}
		}
		return null;
	}

	/**
	 * @see org.spleen.collection.CacheRemoveable#remove(java.lang.Object)
	 */
	@Override
	public void remove(Object key) {
		for (CacheContainer cacheContainer : this.cacheContainers) {
			if(cacheContainer.get(key) != null){
				this.size -= cacheContainer.get(key).getSize();
				cacheContainer.remove(key);
			}else{
				continue;
			}
		}
	}

	/**
	 * @see org.spleen.collection.CacheRemoveable#removeFirst()
	 */
	@Override
	public void removeFirst() {
		this.cacheContainers.get(this.cacheObjectOrder.getFirst()).removeFirst();
		this.cacheObjectOrder.removeFirst();
	}

	/**
	 * @see org.spleen.collection.CacheRemoveable#getConfig()
	 */
	@Override
	public SpleenConfigurator getConfig() {
		return this.config;
	}

	/**
	 * @see org.spleen.collection.CacheRemoveable#getCount()
	 */
	@Override
	public int getCount() {
		int count = 0;
		for (CacheContainer cacheContainer : this.cacheContainers) {
			count += cacheContainer.getCount();
		}
		
		return count;
	}

	/**
	 * @see org.spleen.type.Sizeable#getSize()
	 */
	@Override
	public double getSize() {
		return this.size;
	}

	/**
	 * @see org.spleen.collection.NamespaceInterface#add(java.lang.String, org.spleen.type.CacheObject)
	 */
	@Override
	public void add(String key, CacheObject cacheObject) {
		if(++this.containerTarget < this.containerCount){
			//nothing
		}else{
			this.containerTarget = 0;
		}

		this.cacheContainers.get(this.containerTarget).add(key, cacheObject);
	}

	/**
	 * @see org.spleen.collection.NamespaceInterface#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		boolean emptyState = true;
		for (CacheContainer cacheContainer : cacheContainers) {
			emptyState = emptyState && cacheContainer.isEmpty();
			if(!emptyState){
				return false;
			}else{
				//nothing
			}
		}
		return true;
	}

	/**
	 * @see org.spleen.collection.NamespaceInterface#up(java.lang.String)
	 */
	@Override
	public void up(String key) {
		for (CacheContainer cacheContainer : this.cacheContainers) {
			if(cacheContainer.get(key) != null){
				cacheContainer.up(key);
				break;
			}else{
				//nothing
			}
		}

	}

	/**
	 * @see org.spleen.collection.NamespaceInterface#getCacheObjectOrder()
	 */
	@Override
	public LinkedList<String> getCacheObjectOrder() {
		return null;
	}

}
