package com.tencent.cacheone.caffeineCache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 观自在
 * @description
 * @date 2025-11-24 23:31
 */
public class CaffeineLoading {

    public static void main(String[] args) throws Exception{
        String key1 = "key1";

        //获取 key1 对应的值
        //如果没有获取到则通过在构建同步缓存的时候调用createTestValue方法写入方法值
        Object o = cache.get(key1);
        System.out.println(o);

        if (o != null) {
            cache.put(key1,o);
        }

        Thread.sleep(1999);

        //批量获取
        Map<String,Object> all = cache.getAll(Arrays.asList("key1", "key2", "key3"));
        System.out.println(all);
    }

    static LoadingCache<String,Object> cache= Caffeine.newBuilder()
            .expireAfterWrite(2000, TimeUnit.MILLISECONDS)
            .build(CaffeineLoading::createTestValue);

    static Object createTestValue(String key) {
        return key+"-"+System.currentTimeMillis();
    }
}
