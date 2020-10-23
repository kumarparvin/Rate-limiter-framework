package org.eggShell.tests;

import org.eggShell.reposit.SlidingWindowDataRepository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestDataRepositoryImpl extends SlidingWindowDataRepository {


    Map<String, Map<Long, Long>> dataMap = new ConcurrentHashMap<>(100, 2f, 20);

    @Override
    protected Map<Long, Long> getByKeyAndGreaterThanEqualWindowStartTime(String key, Long windowStartTime) {

        Map<Long, Long> data = dataMap.getOrDefault(key, new LinkedHashMap<>());
        data.entrySet().removeIf(entry -> entry.getKey() < windowStartTime);
        return data;

    }

    @Override
    protected void updateMultiByKey(String key, Map<Long, Long> data) {
        dataMap.put(key, data);
        return;
    }
}
