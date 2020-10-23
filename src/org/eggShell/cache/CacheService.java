package org.eggShell.cache;

public interface CacheService<T> {

    T getByKey(String key);

}
