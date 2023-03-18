package com.example.common.v0.asyn;

import java.util.concurrent.*;

/**
 * 线程池作业
 */
public class CachedThreadPool {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(100);
    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    /**
     * 执行一个一次性任务
     *
     * @param task .
     */
    public static <T> Future<T> submit(Callable<T> task) {
        return executorService.submit(task);
    }

    public static Future<?> submit(Runnable task) {
        return executorService.submit(task);
    }

    /**
     * 执行一个一次性的任务
     *
     * @param task 任务
     */
    public static void execute(Runnable task) {
        executor.schedule(task, 10, TimeUnit.MILLISECONDS);
    }

    /**
     * 执行一个一次性的任务
     *
     * @param task 任务
     */
    public static void execute(Runnable task, long delay, TimeUnit unit) {
        executor.schedule(task, delay, unit);
    }

    /**
     * 提交一个定时执行的任务
     *
     * @param task         任务
     * @param initialDelay 提交任务后延时启动
     * @param period       两次任务的间隙
     * @param timeUnit     时间单位
     */
    public static void scheduleAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit timeUnit) {
        executor.scheduleAtFixedRate(task, initialDelay, period, timeUnit);
    }

    public static void shutdown() {
        executorService.shutdown();
    }
}
