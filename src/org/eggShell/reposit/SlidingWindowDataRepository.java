package org.eggShell.reposit;

import org.eggShell.lock.MutexLock;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class SlidingWindowDataRepository implements DataRepository {

    private MutexLock mutexLock;

    public SlidingWindowDataRepository() {
        mutexLock = MutexLock.Factory.getInstance(397, 10);
    }

    @Override
    final public Long incrAndGetCount(String key, Long windowStartTime, Long currentRequestTime) {
        synchronized (mutexLock.getMutexLockObject(key)) {
            Map<Long, Long> data = getByKeyAndGreaterThanEqualWindowStartTime(key, windowStartTime);

            data = data!=null?data:new HashMap<>(1);
            Long val = data.get(currentRequestTime);
            data.put(currentRequestTime, val!=null?val+1:1);
            Iterator<Map.Entry<Long, Long>> iterator = data.entrySet().iterator();

            Long count=0L;
            while(iterator.hasNext()) {
                Map.Entry<Long, Long> entry = iterator.next();
                if(entry.getKey()<windowStartTime) {
                    System.out.println("removed: " + entry.getKey() + ">>>>" + key);
                    iterator.remove();
                } else {
                    count+=entry.getValue();
                }
            }
            updateMultiByKey(key, data);
            return count;
        }
    }

    protected abstract Map<Long, Long> getByKeyAndGreaterThanEqualWindowStartTime(String key, Long windowStartTime);

    protected abstract void updateMultiByKey(String key, Map<Long, Long> data);
}
