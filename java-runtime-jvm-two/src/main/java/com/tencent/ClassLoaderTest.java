package com.tencent;

/**
 * @author 观自在
 * @date 2025-11-23 20:02
 *
 * 查看类的加载器实例
 */
public class ClassLoaderTest {
    public void foo() {
        System.out.println("hello");
    }

    public static void main(String[] args) throws ClassNotFoundException {
//        加载核心类库的 bootstrap classloader
        System.out.println("核心类库加载器："+ClassLoaderTest.class.getClassLoader()
                .loadClass("java.lang.String").getClassLoader());

//        加载扩展库的 extension classloader
        System.out.println("扩展类库加载器："+ClassLoaderTest.class.getClassLoader()
                .loadClass("com.sun.nio.zipfs.ZipCoder").getClassLoader());

//        加载应用程序的
        System.out.println("应用程序库加载器："+ClassLoaderTest.class.getClassLoader());

//        双亲委派模型 parents delegation model
        System.out.println(
                "应用程序库加载器的父类："+ClassLoaderTest.class.getClassLoader().getParent());
        System.out.println(
                "应用程序库加载器的父类的父类："+ClassLoaderTest.class.getClassLoader()
                        .getParent().getParent());
    }
}
