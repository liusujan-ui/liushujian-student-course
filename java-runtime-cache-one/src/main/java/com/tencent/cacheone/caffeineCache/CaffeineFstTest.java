package com.tencent.cacheone.caffeineCache;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author 观自在
 * @description
 * @date 2025-11-24 23:15
 */
public class CaffeineFstTest {
    static Cache<String, Object> cache = Caffeine.newBuilder()
            .expireAfterWrite(2000, TimeUnit.MILLISECONDS) // 基于时间失效->写入之后开始计时失效
            .maximumSize(10_000)
            .build();
    public static void main(String[] args) throws Exception{
        //返回key + 当前时间戳作为value
        Function<Object,Object> getFuc=key->key+"_"+System.currentTimeMillis();

        String key1 = "key1";

        //获取key1 对应的值，如果获取不到则执行getFuc
        Object value=cache.get(key1,getFuc);
        System.out.println(value);

        //让缓存到期
        Thread.sleep(2001);

        //获取key1对应的值，如果获取不到则返回null
        value= cache.getIfPresent(key1);
        System.out.println(value);

        //设置key1的值
        cache.put(key1,"putValue");
        value=cache.get(key1,getFuc);
        System.out.println(value);

        ConcurrentMap<String, Object> map = cache.asMap();
        System.out.println(map);

        //删除key1
        cache.invalidate(key1);
        map=cache.asMap();
        System.out.println(map);
    }
}
