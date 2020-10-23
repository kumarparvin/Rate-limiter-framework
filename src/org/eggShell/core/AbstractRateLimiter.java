package org.eggShell.core;

import org.eggShell.reposit.ConfigRepository;
import org.eggShell.reposit.DataRepository;

public abstract class AbstractRateLimiter implements RateLimiter {

    protected ConfigRepository configRepository;
    protected DataRepository dataRepository;

    protected AbstractRateLimiter(ConfigRepository configRepository1, DataRepository dataRepository1) {
        configRepository = configRepository1;
        dataRepository = dataRepository1;
    }

    abstract public boolean permitted(String rateLimiterConfigKeyName, String requestKey);

    protected long windowStartTime(long currentMillis, long windowTimeInMillis) {

        return currentMillis - windowTimeInMillis;
    }

}
