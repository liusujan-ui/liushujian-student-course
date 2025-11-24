package com.tencent.cacheone.ehCache;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author 观自在
 * @description
 * @date 2025-11-24 22:52
 */
public class EhcacheTest {
    public static void main(String[] args) {
        //初始化Ehcache对象
        CacheManager cacheManager = new CacheManager();
        //加载自定义cache对象
        Cache cache = cacheManager.getCache("simpleCache");
        //把集合放入缓存，存放键值对类似map
        cache.put(new Element("user","zhangsan"));
        //取出集合根据key获取值
        System.out.println("key=user value="+cache.get("user").getObjectValue());
        //更新集合的key=user的值
        cache.put(new Element("user","lisi"));
        System.out.println("key=user value="+cache.get("user").getObjectValue());
        //获取缓存中的元素个数
        System.out.println("集合个数："+cache.getSize());
        //移除cache某个值
        cache.remove("user");
        System.out.println("集合个数："+cache.getSize());
        //关闭当前cacheManager对象
        cacheManager.shutdown();
    }
}
