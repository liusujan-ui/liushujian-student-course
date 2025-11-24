package com.tencent.cacheone.guavaCache;

import ch.qos.logback.core.util.Loader;
import com.google.common.cache.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author 观自在
 * @description guava只能将数据存储到本地缓存，如果有其他的需求，建议使用memoCache或者是redis
 * @date 2025-11-24 21:15
 */
public class GuavaLoaderTest {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            String key = "key" + i;
            String value = "value" + i;
            cache.put(key, value);
            System.out.println("["+key+":"+value+"] is put into cache!");
        }

//        如果存在就获取
        System.out.println(cache.getIfPresent("key6"));

        try {
//            不存在key，会报错
            System.out.println(cache.get("key"));
        } catch (Exception e) {
            System.out.println("不存在的key，会报错");
        }
    }

    /**
     * 加载方式1：CacheLoader
     * 1.设置缓存容量
     * 2.设置超时时间
     * 3.提供移除监听器
     * 4.提供缓存加载器
     * 5.构建缓存
     */
//    提供缓存加载器
    static CacheLoader<String,String> loader=new CacheLoader<String, String>() {
        @Override
        public String load(String key) throws Exception {
            Thread.sleep(1000);
            if ("key".equals(key)) {
                return null;
            }
            System.out.println(key+" is loaded from a cacheLoader!");
            return key+"'s value";
        }
    };

    static RemovalListener<String,String> removalListener= new RemovalListener<String, String>() {
        @Override
        public void onRemoval(RemovalNotification<String, String> removal) {
            System.out.println("["+removal.getKey()+":"+removal.getValue()+"] is removed!");
        }
    };

    static LoadingCache<String,String> cache = CacheBuilder.newBuilder()
            .maximumSize(5) //设置缓存容量
            .expireAfterAccess(10, TimeUnit.MINUTES) //设置超时时间
            .expireAfterWrite(10, TimeUnit.MINUTES) //设置没有被访问
            .removalListener(removalListener) //提供移除监听器
            .build(loader); //提供缓存加载器 loader，构建缓存

}
