package org.eggShell.config;

public class RateLimiterConfig {

    private long windowTimeMillis;
    private long requestPermitted;

    public RateLimiterConfig(long windowTimeMillis, long requestPermitted) {
        this.windowTimeMillis = windowTimeMillis;
        this.requestPermitted = requestPermitted;
    }



    public long getWindowTimeMillis() {
        return windowTimeMillis;
    }

    public long getRequestPermitted() {
        return requestPermitted;
    }

}
