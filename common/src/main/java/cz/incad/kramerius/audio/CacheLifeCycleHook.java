package cz.incad.kramerius.audio;

import java.util.logging.Logger;

import com.google.inject.Inject;

import cz.incad.kramerius.service.LifeCycleHook;
import org.ehcache.CacheManager;

public class CacheLifeCycleHook implements LifeCycleHook {

	public static Logger LOGGER = Logger.getLogger(CacheLifeCycleHook.class.getName());
	
	@Inject
	private CacheManager cacheManager;
	
	@Override
	public void shutdownNotification() {
		LOGGER.info("shutting down Ehcache Manager");
		cacheManager.close();
	}
	

	@Override
	public void startNotification() {
		
	}

}
