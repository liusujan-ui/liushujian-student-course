package com.tencent.cacheone;

import com.tencent.cacheone.mapCache.CacheProvider;
import com.tencent.cacheone.mapCache.FIFOCacheProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class JavaRuntimeCacheOneApplicationTests {

    @Test
    void test() throws InterruptedException {
        CacheProvider cacheProvider = new CacheProvider();

        String key = "key";

        //不设置过期时间
        System.out.println("不设置过期时间");
        cacheProvider.put(key, 111);
        System.out.println("key:" + key+",value:" + cacheProvider.get(key));
        System.out.println("key:" + key+",value:" + cacheProvider.remove(key));
        System.out.println("key:" + key+",value:" + cacheProvider.get(key));

        //设置过期时间
        System.out.println("设置过期时间");
        cacheProvider.put(key, 222,1000L);
        System.out.println("key:" + key+",value:" + cacheProvider.get(key));
        Thread.sleep(1000L);
        System.out.println("key:" + key+",value:" + cacheProvider.get(key));
        Thread.sleep(1L);
        System.out.println("key:" + key+",value:" + cacheProvider.get(key));

    }

    @Test
    void test1() throws Exception {
        FIFOCacheProvider cacheProvider = new FIFOCacheProvider(10);
        for (int i = 0; i < 15; i++) {
            cacheProvider.put(i+":id", UUID.randomUUID());
        }
        System.out.println(cacheProvider.size()+"\n"+cacheProvider.toString());
    }

}
