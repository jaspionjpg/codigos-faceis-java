package cacheservice;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

    private static ServiceLocator serviceLocator;

    private Map<String, String> cacheKeyMap;

    static {
        serviceLocator = new ServiceLocator();
    }

    private ServiceLocator() {
        cacheKeyMap = Collections.synchronizedMap(new HashMap<String, String>());
    }

    static public ServiceLocator getInstance() {
        return serviceLocator;
    }


    public boolean cacheExist(String key) {
        return getInstance().cacheKeyMap.containsKey(key);
    }

    public void removeAllCache() {
        this.cacheKeyMap.clear();
    }

    public String getCacheAtlas(String key) {
        return cacheKeyMap.get(key);
    }

    public void putCacheAtlas(String key, String body) {
        cacheKeyMap.put(key, body);
    }

    public void removeCacheAtlasByKey(String key) {
        cacheKeyMap.remove(key);
    }
}
