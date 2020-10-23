package org.eggShell.lock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MutexLock {

    private final Map<String, Mutex> mutexMap;
    private final int totalLocks;

    private MutexLock(int totalLocks, int concurrency) {
        mutexMap = new ConcurrentHashMap<>(totalLocks, 0.75f, concurrency);
        this.totalLocks= totalLocks;
    }

    public static class Factory {
        private static  MutexLock mutexLock;
        public static MutexLock getInstance(int totalLocks, int concurrency) {
            if(mutexLock==null) {
                synchronized (Factory.class) {
                    if(mutexLock == null) {
                        mutexLock = new MutexLock(totalLocks, concurrency);
                    }
                }
            }
            return mutexLock;
        }
    }

    public static class Mutex {

        private String key;

        public Mutex(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    private String hasKey(String key) {
        return String.valueOf(key.hashCode()%totalLocks);
    }

    public Mutex getMutexLockObject(String id) {
        String key = hasKey(id);
        if(mutexMap.get(key)!=null) {
            return mutexMap.get(key);
        }
        synchronized (MutexLock.class) {
            Mutex mutex = new Mutex(id);
            mutexMap.put(key, mutex);
            return mutex;
        }
    }



}
