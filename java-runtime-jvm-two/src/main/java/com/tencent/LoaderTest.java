package com.tencent;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author 观自在
 * @date 2025-11-23 20:18
 *
 * class loader 不会重复加载
 *
 * 双亲委派模型，加载
 */
public class LoaderTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("file:D:\\"); //jvm 类放的位置
        while (true){
//            创建一个新的类加载器
            URLClassLoader loader = new URLClassLoader(new URL[]{url});

            Class clazz = loader.loadClass("com.tencent.ClassLoaderTest");
            System.out.println("classLoaderTest所使用的类加载器："+clazz.getClassLoader());
            Object o = clazz.newInstance();
            Object main = clazz.getMethod("foo").invoke(o);
            System.out.println("调用getValue获得的返回值为："+main);

            Thread.sleep(3000L);
            System.out.println();
        }
    }
}
