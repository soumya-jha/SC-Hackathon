package com.hackerrank.sample.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class RateLimiter {
    private Semaphore semaphore;
    private int maxPermits;
    private TimeUnit timePeriod;
    private ScheduledExecutorService scheduler;
    
    private static RateLimiter limiter;

    public static  RateLimiter create(int permits, TimeUnit timePeriod) {
    	
    	if(limiter==null) {
    	 limiter = new RateLimiter(permits, timePeriod);
    	}
       
        limiter.schedulePermitReplenishment();
        return limiter;
    }

    private RateLimiter(int permits, TimeUnit timePeriod) {
        this.semaphore = new Semaphore(permits);
        this.maxPermits = permits;
        this.timePeriod = timePeriod;
    }

    public boolean tryAcquire() {
        return semaphore.tryAcquire();
    }

    public void stop() {
        scheduler.shutdownNow();
    }

    public void schedulePermitReplenishment() {
        scheduler = Executors.newScheduledThreadPool(1);
      
        scheduler.schedule(() -> {
            semaphore.release(maxPermits - semaphore.availablePermits());
        }, 1, timePeriod);

    }
    
public static void killRateLimiter() {
    	
    	if(limiter!=null) {
    		limiter=null;
    	}
    }
}