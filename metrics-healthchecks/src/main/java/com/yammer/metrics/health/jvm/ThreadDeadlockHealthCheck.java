package com.yammer.metrics.health.jvm;

import com.yammer.metrics.health.HealthCheck;
import com.yammer.metrics.jvm.ThreadDeadlockDetector;

import java.util.Set;

/**
 * A health check which returns healthy if no threads are deadlocked.
 */
public class ThreadDeadlockHealthCheck extends HealthCheck {
    private final ThreadDeadlockDetector detector;

    /**
     * Creates a new health check.
     */
    public ThreadDeadlockHealthCheck() {
        this(new ThreadDeadlockDetector());
    }

    /**
     * Creates a new health check with the given detector.
     *
     * @param detector a thread deadlock detector
     */
    public ThreadDeadlockHealthCheck(ThreadDeadlockDetector detector) {
        this.detector = detector;
    }

    @Override
    protected Result check() throws Exception {
        final Set<String> threads = detector.getDeadlockedThreads();
        if (threads.isEmpty()) {
            return Result.healthy();
        }
        return Result.unhealthy(threads.toString());
    }
}
