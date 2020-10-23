package org.eggShell.reposit;

import org.eggShell.config.RateLimiterConfig;

public interface ConfigRepository {

    RateLimiterConfig getByKey(String key);
}
