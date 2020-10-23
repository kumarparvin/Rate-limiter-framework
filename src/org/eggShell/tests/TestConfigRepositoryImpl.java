package org.eggShell.tests;

import org.eggShell.config.RateLimiterConfig;
import org.eggShell.constants.RateLimiterConstants;
import org.eggShell.reposit.ConfigRepository;

import java.util.HashMap;
import java.util.Map;


public class TestConfigRepositoryImpl implements ConfigRepository {

    private final static Map<String, RateLimiterConfig> configMap = new HashMap<>();

    static {
        configMap.put(TestConstants.RATE_LIMITER_CONFIG_KEY, new RateLimiterConfig(0, 1));
        configMap.put(RateLimiterConstants.DEFAULT_CONFIG_KEY, new RateLimiterConfig(1000, 10));
    }

    @Override
    public RateLimiterConfig getByKey(String key) {
        if(key!=null) {
            return configMap.get(key);
        }
        return null;
    }
}
