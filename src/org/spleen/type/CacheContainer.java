/**
 * 
 */
package org.spleen.type;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import org.spleen.collection.NamespaceInterface;
import org.spleen.collection.NamespaceMultiThread;
import org.spleen.config.SpleenConfigurator;
import org.spleen.threading.CacheOutRemover;

/**
 * Cache Container.
 * 
 * Looking like NamespaceMap class
 * with parent instance.
 * 
 * @author vallance
 *
 */
public class CacheContainer implements NamespaceInterface {

	protected ConcurrentHashMap<String, CacheObject> namespaces;
	protected LinkedList<String> cacheObjectOrder;
	private CacheOutRemover cacheOutRemover;
	private Thread cacheOutThread;
	private NamespaceMultiThread parent;
	private double size = 0;
	
	/**
	 * Default constructor.
	 * 
	 * Default CacheContainer constructor
	 * that initialize the remover Thread
	 * 
	 * @param namespaceMultiThread 	The parent NamespaceMultiThread instance.
	 */
	public CacheContainer(NamespaceMultiThread namespaceMultiThread) {
		this.parent = namespaceMultiThread;
		
		this.cacheOutRemover = new CacheOutRemover(this);
		this.cacheOutThread = new Thread(this.cacheOutRemover);
		this.namespaces = new ConcurrentHashMap<String, CacheObject>();
		this.cacheObjectOrder = new LinkedList<String>();
		
		this.cacheOutThread.setDaemon(true);
		this.cacheOutThread.start();
	}

	/**
	 * @see org.spleen.type.Sizeable#getSize()
	 */
	@Override
	public double getSize() {
		return this.size;
	}

	/**
	 * @see org.spleen.collection.CacheRemoveable#keySet()
	 */
	@Override
	public Object[] keySet() {
		return this.namespaces.keySet().toArray();
	}

	/**
	 * @see org.spleen.collection.CacheRemoveable#get(java.lang.Object)
	 */
	@Override
	public CacheObject get(Object key) {
		if(this.namespaces.containsKey(key)){
			return this.namespaces.get(key);
		}else{
			return null;
		}
	}

	/**
	 * @see org.spleen.collection.CacheRemoveable#remove(java.lang.Object)
	 */
	@Override
	public synchronized void remove(Object key) {
		if(this.namespaces.containsKey(key)){
			this.size -= this.namespaces.get(key).getSize();
			this.namespaces.remove(key);
			this.cacheObjectOrder.remove(key);
		}
	}

	/**
	 * @see org.spleen.collection.CacheRemoveable#removeFirst()
	 */
	@Override
	public synchronized void removeFirst() {
		this.size -= this.namespaces.get(this.cacheObjectOrder.getFirst()).getSize();
		this.namespaces.remove(this.cacheObjectOrder.getFirst());
		this.cacheObjectOrder.removeFirst();
	}

	/**
	 * @see org.spleen.collection.CacheRemoveable#getConfig()
	 */
	@Override
	public SpleenConfigurator getConfig() {
		return this.parent.getConfig();
	}

	/**
	 * @see org.spleen.collection.CacheRemoveable#getCount()
	 */
	@Override
	public int getCount() {
		return this.namespaces.size();
	}

	/**
	 * @see org.spleen.collection.NamespaceInterface#add(java.lang.String, org.spleen.type.CacheObject)
	 */
	@Override
	public synchronized void add(String key, CacheObject cacheObject) {
		cacheObject.init(this.parent.getConfig());
		this.namespaces.put(key, cacheObject);
		this.cacheObjectOrder.add(key);
		this.size += cacheObject.getSize();
	}

	/**
	 * @see org.spleen.collection.NamespaceInterface#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return this.namespaces.isEmpty();
	}

	/**
	 * @see org.spleen.collection.NamespaceInterface#up(java.lang.String)
	 */
	@Override
	public synchronized void up(String key) {
		this.namespaces.get(key).init(this.parent.getConfig());
		
	}

	/**
	 * @see org.spleen.collection.NamespaceInterface#getCacheObjectOrder()
	 */
	@Override
	public LinkedList<String> getCacheObjectOrder() {
		return cacheObjectOrder;
	}

	/**
	 * Finalize object.
	 * 
	 * Finalize the object and set the
	 * remover observer Thread stop
	 * state to true.
	 * 
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		this.cacheOutRemover.setStop(true);
		super.finalize();
	}

}
