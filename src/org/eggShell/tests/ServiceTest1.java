package org.eggShell.tests;

import org.eggShell.core.RateLimiter;
import org.eggShell.core.SlidingWindowRateLimiter;
import org.eggShell.reposit.ConfigRepository;
import org.eggShell.reposit.DataRepository;
import org.eggShell.reposit.SlidingWindowDataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ServiceTest1 {

    private ConfigRepository configRepository;
    private DataRepository dataRepository;
    private RateLimiter rateLimiter;

    public ServiceTest1(ConfigRepository configRepository, SlidingWindowDataRepository dataRepository) {
        this.configRepository = configRepository;
        this.dataRepository = dataRepository;
        this.rateLimiter = SlidingWindowRateLimiter.getInstance(configRepository, dataRepository);
    }

    public void fetchUserData(String userId) {

        if(rateLimiter.permitted(TestConstants.RATE_LIMITER_CONFIG_KEY, userId)) {
            System.out.println(System.currentTimeMillis()+ " >>>>>> UserId:" + userId + ", Name: Parvin"+ userId);
        } else {
            System.out.println(System.currentTimeMillis()+ " >> Limit breached");
        }
    }


    public static void main(String[] args) {
        ServiceTest1 serviceTest1 = new ServiceTest1(new TestConfigRepositoryImpl(), new TestDataRepositoryImpl());

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletionService<Object> executorCompletionService = new ExecutorCompletionService<>(executorService);

        for(int i=0;i<50;i++) {
            int finalI = i;
            Callable<Object> callable = () -> {
                serviceTest1.fetchUserData(String.valueOf(finalI/5));
                return null;
            };

            executorCompletionService.submit(callable);
        }

        for(int i=0; i<50;i++) {
            try {
               Future<Object> future = executorCompletionService.take();
            } catch (Exception ignore) {
            }
        }

        executorService.shutdown();
    }

}
