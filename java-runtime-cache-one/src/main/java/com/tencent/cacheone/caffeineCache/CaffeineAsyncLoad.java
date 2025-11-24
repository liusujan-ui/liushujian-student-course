package com.tencent.cacheone.caffeineCache;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.checkerframework.checker.nullness.qual.NonNull;


import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author 观自在
 * @description
 * @date 2025-11-24 23:40
 */
public class CaffeineAsyncLoad {


    public static void main(String[] args) throws Exception{
        String key1 = "key1";

        //获取 key1 对应的值
        //异步手动加载返回的不是值
        CompletableFuture<Object> future = cache.get(key1);
        //异步获取结果，对高性能场景有帮助
        future.thenAccept(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                System.out.println(System.currentTimeMillis()+"->"+o);
            }
        });

        Thread.sleep(4000);


        //如果cache中key为空，直接返回null，不为空则异步取值
        CompletableFuture<Object> ifPresent = cache.getIfPresent(key1);
        if(ifPresent==null){
            System.out.println("null");
        }else {
            //异步取值
            ifPresent.thenAccept(new Consumer<Object>() {
                @Override
                public void accept(Object o) {
                    System.out.println(0);
                }
            });
        }

        //批量异步取值，取不到则加载值
        CompletableFuture<Map<String, Object>> all = cache.getAll(Arrays.asList("key1", "key2", "key3"));

        //批量异步获取
        all.thenAccept(new Consumer<Map<String, Object>>() {
            @Override
            public void accept(Map<String, Object> stringObjectMap) {
                System.out.println(stringObjectMap);
            }
        });

        Thread.sleep(2001);

        ConcurrentMap<@NonNull String, @NonNull Object> map = cache.synchronous().asMap();
        System.out.println(map);
    }

    static AsyncLoadingCache<String,Object> cache= Caffeine.newBuilder()
            .expireAfterWrite(2000, TimeUnit.MILLISECONDS)
            .buildAsync(CaffeineLoading::createTestValue);

    private static Object createTestValue(String key) {
        return key+"-"+System.currentTimeMillis();
    }
}
