package org.eggShell.core;

import org.eggShell.config.RateLimiterConfig;
import org.eggShell.reposit.ConfigRepository;
import org.eggShell.reposit.SlidingWindowDataRepository;

public class SlidingWindowRateLimiter extends AbstractRateLimiter {

    private static SlidingWindowRateLimiter slidingWindowRateLimiter;

    private SlidingWindowRateLimiter(ConfigRepository configRepository1, SlidingWindowDataRepository dataRepository1) {
        super(configRepository1, dataRepository1);
    }

    public static SlidingWindowRateLimiter getInstance(ConfigRepository configRepository1, SlidingWindowDataRepository dataRepository1) {

        if(slidingWindowRateLimiter==null) {
            synchronized (SlidingWindowRateLimiter.class) {
                if (slidingWindowRateLimiter == null) {
                    slidingWindowRateLimiter = new SlidingWindowRateLimiter(configRepository1, dataRepository1);
                }
            }
        }
        return slidingWindowRateLimiter;
    }

    @Override
    public boolean permitted(String rateLimiterConfigKeyName, String requestKey) {

        RateLimiterConfig rateLimiterConfig = configRepository.getByKey(rateLimiterConfigKeyName);

        if(rateLimiterConfig==null) {
            return true;
        }

        long currentMillis = System.currentTimeMillis();
        long windowStartTime = windowStartTime(currentMillis, rateLimiterConfig.getWindowTimeMillis());

        Long count = dataRepository.incrAndGetCount(requestKey, windowStartTime, currentMillis);
        if (count != null) {
            return count <= rateLimiterConfig.getRequestPermitted();
        }
        return true;
    }



}
