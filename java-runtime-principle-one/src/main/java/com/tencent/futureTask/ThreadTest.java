package com.tencent.futureTask;

import java.util.concurrent.*;

/**
 * @author 观自在
 * @description  future 这种线程的方式有返回值。
 *                      适合多线程最终形成结果统计的场景
 * @date 2025-12-06 18:33
 */
public class ThreadTest {



    static ExecutorService executorService = Executors.newScheduledThreadPool(2);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> callable=new Callable<String>() {

            @Override
            public String call() throws Exception {
                return "返回数值";
            }
        };

        Future<String> submit = executorService.submit(callable);
        System.out.println(submit.get());
    }
}
