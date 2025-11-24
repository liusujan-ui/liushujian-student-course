package com.tencent.cacheone.guavaCache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.Callable;

/**
 * @author 观自在
 * @description
 * @date 2025-11-24 21:38
 */
public class GuavaCallableTest {

    /**
     * 加载方式2：callable
     * @param args
     */

    public static void main(String[] args) {
        Object o=new Object();
        cache.put("1234",o);
        o=new Object();

//        主动GC
        System.gc();
        System.out.println(cache.getIfPresent("1234"));

        /**
         * hitCount:
         */
        System.out.println(cache.stats());

        System.out.println("=============================");

        cache.put("key", "value");

//        如果存在就获取，不存在就返回null
        System.out.println(cache.getIfPresent("key1"));

        try {
//            获取key为123的缓存数据，如果有就返回，没有就返回call方法的返回值
            System.out.println(cache.get("123", new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return "运算、缓存、然后返回";
                }
            }));

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

//    构建容量为3的缓存对象
    static Cache<String,Object> cache = CacheBuilder.newBuilder()
        .maximumSize(3)
        .weakKeys() //当值没有其他（强或弱）引用时，缓存项可以被垃圾回收
        .recordStats() //开启guava cache的统计功能
        .build();

}
