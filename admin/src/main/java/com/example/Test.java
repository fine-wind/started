package com.example;

import java.util.Random;
import java.util.concurrent.*;

public class Test {


    static final ExecutorService executorService = new ThreadPoolExecutor(
            100,
            1000,
            1,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());

    public static void main(String[] args) throws Exception {

        long l = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            run(new Random().nextInt() % 10);
        }
        System.out.println(System.currentTimeMillis() - l);
        System.exit(0);
    }

    private static void run(int timeoutMS) throws Exception {
        Future<String> future = executorService.submit(() -> {
            int sleep = new Random().nextInt() % 10 + 3;
            long l = System.currentTimeMillis();
            System.out.println("\t\t事务开始, 事务时间：" + sleep + "ms");
            try {
                TimeUnit.MILLISECONDS.sleep(sleep);
            } catch (Exception e) {
                System.out.println("\t\t事务异常");
            }
            System.out.println("\t\t事务结束" + (System.currentTimeMillis() - l));
            System.out.println("\t\t" + Thread.currentThread().getName());
            return "OK";
        });


        long start = System.currentTimeMillis();
        boolean run;
        boolean notOut;
        do {
            TimeUnit.MILLISECONDS.sleep(1);

            run = !future.isDone();
            notOut = System.currentTimeMillis() - start < timeoutMS;
            // System.out.println("\t\t 1次循环 > " + run + "  notOut: " + notOut);
        } while (run && notOut);

        try {
            String result = future.get(1, TimeUnit.MILLISECONDS);
            long l = System.currentTimeMillis() - start;
            boolean b = l + 2 > timeoutMS;
            if (b) {
                System.err.println("耗时：" + l + "ms");
            } else {
                System.out.println(result + "耗时：" + l + "ms");
            }
        } catch (Exception e) {
            System.out.println("\t任务超时。");
        } finally {
            future.cancel(true);
            System.out.println("\t清理资源。");
        }
    }
}



