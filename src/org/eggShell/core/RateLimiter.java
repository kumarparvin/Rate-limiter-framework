package org.eggShell.core;

public interface RateLimiter {

    boolean permitted(String rateLimiterConfigKeyName, String requestKey);
}
