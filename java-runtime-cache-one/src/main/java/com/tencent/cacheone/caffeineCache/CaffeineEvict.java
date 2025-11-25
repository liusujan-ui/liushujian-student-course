package com.tencent.cacheone.caffeineCache;

import com.github.benmanes.caffeine.cache.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author 观自在
 * @description caffeine 操作具有原子性
 * @date 2025-11-24 23:54
 */
public class CaffeineEvict {
    public static void main(String[] args) throws Exception {
        LoadingCache<Object, Object> cache = Caffeine.newBuilder()
                // 基于时间失效，写入之后开始计时失效
                .expireAfterWrite(2000, TimeUnit.MILLISECONDS)
                //or 基于时间失效，访问之后开始计时失效 access
                .executor(Executors.newSingleThreadExecutor())
                .removalListener(new RemovalListener<Object, Object>() {

                    @Override
                    public void onRemoval(@Nullable Object o, @Nullable Object o2, @NonNull RemovalCause removalCause) {
                        System.out.println("缓存失效了 removed " + o + " cause " + removalCause);
                    }
                }) //同步加载和手动加载的区别就是在构建缓存时提供一个同步的加载方法
                .build(new CacheLoader<Object, Object>() {
                    //单个 key 值加载
                    @Override
                    public Object load(@NonNull Object o) throws Exception {
                        System.out.println("-----exec load-----");
                        return o + "_" + System.currentTimeMillis();
                    }
                });

        cache.put("key1", "value1");

        Thread.sleep(2001);
        Object o = cache.get("key1");
        System.out.println("新值："+o);
        System.exit(1);
    }
    public static void numEvict() {
        Cache<Object, Object> cache = Caffeine.newBuilder()
                .maximumSize(20)
                .removalListener(new RemovalListener<Object, Object>() {
                    @Override
                    public void onRemoval(@Nullable Object o, @Nullable Object o2, @NonNull RemovalCause removalCause) {
                        System.out.println("removal " + o + " cause " + removalCause);
                    }
                })
                .build();

        for (int i = 0; i < 25; i++) {
            cache.put("key" + i, "value" + i);
        }

        cache.cleanUp();

    }
}
