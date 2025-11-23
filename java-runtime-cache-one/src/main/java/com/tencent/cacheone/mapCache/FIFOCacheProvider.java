package com.tencent.cacheone.mapCache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 观自在
 * @description 基于map的缓存工具
 * @date 2025-11-23 22:49
 */


public class FIFOCacheProvider {
//    存放缓存的集合
    private static Map<String, CacheData> datas = null;
//    定时器线程池，用于清除过期的缓存
    private final static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);

//    FIFO
    private static int MAX_CACHE_SIZE = 0;
    private final float LOAD_FACTOR = 0.75f;

    //构造函数，需要设置缓存的大小
    public FIFOCacheProvider(int maxCacheSize) {
        MAX_CACHE_SIZE = maxCacheSize;
        //根据cachesize 和填充因子计算cache的容量
        int capacity = (int) (Math.ceil(MAX_CACHE_SIZE/LOAD_FACTOR)+1);
        datas = new LinkedHashMap<String,CacheData>(capacity,LOAD_FACTOR,false){
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, CacheData> eldest) {
                return size() > MAX_CACHE_SIZE;
            }
        };
    }

//    获取
    public synchronized <T> T get(String key) {
        CacheData cacheData = datas.get(key);
        return cacheData == null ? null : (T) cacheData.data;
    }
//    设置
    public synchronized void put(String key,Object value) {
        this.put(key,value,-1L);
    }

    public synchronized void put(String key,Object value,long expire) {
//        清除原数据
        datas.remove(key);
        if(expire>0) {
            executor.schedule(new Runnable() {
                @Override
                public void run() {
                    // 过期后清除缓存
                    synchronized (this) {
                        datas.remove(key);
                    }
                }
            },expire, TimeUnit.MILLISECONDS);
            datas.put(key,new CacheData(value,expire));
        }else {
//            不设置过期时间
            datas.put(key,new CacheData(value,-1L));
        }

    }
//    删除
    public synchronized <T> T remove(String key) {
        CacheData remove = datas.remove(key);
        return remove==null?null:(T) remove.data;
    }

//    总数
    public synchronized int size() {
        return datas.size();
    }

    /**
     * 缓存实体类
     */
    @Data
    @AllArgsConstructor
    public class CacheData{
//        缓存数据
        public Object data;
//        失效时间
        public long expire;

    }


    @Override
    public String toString() {
        StringBuffer sb=new StringBuffer();
        for(Map.Entry<String,CacheData> entry:datas.entrySet()) {
            sb.append(entry.getKey()+"="+entry.getValue().data+"\n");
        }
        return sb.toString();
    }
}
