package cz.incad.kramerius.impl;

import cz.incad.kramerius.ObjectPidsPath;
import cz.incad.kramerius.SolrAccess;
import cz.incad.kramerius.utils.conf.KConfiguration;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;

import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * CachedSolrAccessImpl
 *
 * @author Martin Rumanek
 */
public class CachedSolrAccessImpl extends SolrAccessImpl implements SolrAccess {

    private Cache<String, ObjectPidsPath[]> cache;

    private static final String CACHE_ALIAS = "SolrPathCache";

    @Inject
    public CachedSolrAccessImpl(CacheManager cacheManager, KConfiguration configuration) {
        cache = cacheManager.getCache(CACHE_ALIAS, String.class, ObjectPidsPath[].class);
        if (cache == null) {
            cache = cacheManager.createCache(CACHE_ALIAS,
                    CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, ObjectPidsPath[].class,
                            ResourcePoolsBuilder.heap(1000).offheap(32, MemoryUnit.MB))
                           .withExpiry(Expirations.timeToLiveExpiration(
                                   Duration.of(configuration.getCacheTimeToLiveExpiration(), TimeUnit.SECONDS))).build());
        }
    }

    @Override
    public ObjectPidsPath[] getPath(String pid) throws IOException {
        ObjectPidsPath[] path = cache.get(pid);

        if (path != null) { //cache hit
            return path;
        } else { //cache miss
            path = super.getPath(pid);
            cache.put(pid, path);
            return path;
        }
    }
}
