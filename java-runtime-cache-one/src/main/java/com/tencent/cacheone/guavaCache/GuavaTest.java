package com.tencent.cacheone.guavaCache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.atomic.AtomicInteger;

public class GuavaTest {

    public static void main(String[] args) throws Exception{
        GuavaTest test = new GuavaTest();
        test.test();
    }


    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    public void test() throws Exception {
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(500)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        Thread.sleep(1000);
                        return atomicInteger.incrementAndGet() + "";
                    }
                });

        cache.get("test");
        cache.invalidate("test");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String value=cache.get("test");
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                long start=System.currentTimeMillis();
                cache.invalidate("test");
                System.out.println("use ms:"+(System.currentTimeMillis()-start));
            }
        }).start();

        Thread.sleep(1200);
        System.out.println("========"+cache.asMap());
        System.out.println("========"+cache.get("test"));

    }
}
