package org.spleen.example;

import org.spleen.Spleen;
import org.spleen.config.SpleenConfigurator;
import org.spleen.tool.SizeConverter;
import org.spleen.type.CacheObject;
import org.spleen.type.SimpleCacheObject;

public class SpleenExample {

	public static void main(String[] args) {
		
		//define new spleen
		Spleen mainSpleen = new Spleen();
		
		//create a simple namespace indexed under "simpleNamespace" key
		mainSpleen.createNamespace("simpleNamespace", configExample());
		
		/*
		 * create a multi-threaded namespace indexed under "multiThreadNamespace" key
		 * 
		 * A multi-threaded namespace will create some subnamespace with associated
		 * remover thread.
		 * 
		 * This namespace type allow superior memory suppression. This is more
		 * usable for very large instance storage, but need more time for access
		 * and adding.
		 */
		mainSpleen.createNamespace(10, "multiThreadNamespace",configExample());
		
		//Create cachable objects
		SimpleCacheObject integer12 = new SimpleCacheObject(new Integer(12));
		SimpleCacheObject double24 = new SimpleCacheObject(new Double(24));
		
		SimpleCacheObject integer05 = new SimpleCacheObject(new Integer(5));
		SimpleCacheObject double12 = new SimpleCacheObject(new Double(12));
		
		//register objects
		mainSpleen.put("simpleNamespace", "integer12", integer12);
		mainSpleen.put("simpleNamespace", "double24", double24);

		mainSpleen.put("multiThreadNamespace", "integer05", integer05);
		mainSpleen.put("multiThreadNamespace", "double12", double12);
		
		//get objects
		CacheObject integer12Rec = mainSpleen.get("simpleNamespace", "integer12");
		CacheObject double24Rec = mainSpleen.get("simpleNamespace", "double24");
		
		CacheObject integer05Rec = mainSpleen.get("multiThreadNamespace", "integer05");
		CacheObject double12Rec = mainSpleen.get("multiThreadNamespace", "double12"); 
		
		//Get stored objects
		System.out.println("integer12Rec : "+integer12Rec.getCacheObject());
		System.out.println("double24Rec : "+double24Rec.getCacheObject());
		System.out.println("integer05Rec : "+integer05Rec.getCacheObject());
		System.out.println("double12Rec : "+double12Rec.getCacheObject());
		
		//wait for remover thread completely clear the namespaces
		System.out.println("Start to wait elements deletion. (Configured to be removed after 10 seconds (+/- 500 for the scheduler))");
		long start = System.currentTimeMillis();
		while(!mainSpleen.get("simpleNamespace").isEmpty() && !mainSpleen.get("simpleNamespace").isEmpty());
		System.out.println("Each elements deleted into "+((System.currentTimeMillis()-start) / (double)1000)+" seconds.");
	}
	
	public static SpleenConfigurator configExample(){
		/*
		 * Define base configuration.
		 * 
		 * Spleen configurator set automatically undefined
		 * parameters to the following :
		 * 	- Remover schedule : 5 milliseconds
		 * 	- Cache timeout : 1 second
		 * 	- Maximum memory size : 1 megabit
		 * 	- Maximum entry count : 100 instances
		 */
		SpleenConfigurator baseConfig = new SpleenConfigurator();
		
		//Set up remover schedule to 500 milliseconds :
		baseConfig.setRemoverSheduleTime(500);
		
		//Set up timeout to 10 seconds :
		baseConfig.setCacheTimeout(10_000);
		
		//Set up memory size to 5 kilobit :
		baseConfig.setMaxSize(5120);
		//or with SizeConverter :
		baseConfig.setMaxSize(SizeConverter.convert(5, SizeConverter.K));
		
		//Set up maximum entry count to 10 instances :
		baseConfig.setMaxCount(10);
		
		baseConfig = null;
		/*
		 * Define the same configuration with constructor :
		 */
		baseConfig = new SpleenConfigurator(500, 10_000, 5120, 10);
		
		return baseConfig;
	}

}
