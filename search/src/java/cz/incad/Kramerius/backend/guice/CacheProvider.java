package cz.incad.Kramerius.backend.guice;

import com.google.inject.Provider;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;



/**
 * CacheProvider
 *
 * @author Martin Rumanek
 */
public class CacheProvider implements Provider<CacheManager> {

    @Override
    public CacheManager get() {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
        cacheManager.init();
        return cacheManager;
    }
}
