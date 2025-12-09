package com.tencent.JVMAnalysis;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author 观自在
 * @description 逃逸分析
 * 关闭逃逸分析：-XX:DoEscapeAnalysis
 *
 * 逃逸分析 & 栈上分配 & 标量替换  默认开启>=1.6u23
 * -XX:PrintFlagsFinal 启动时查看参数 -XX:PrintGC 查看GC
 * 关闭 -XX:-DoEscapeAnalysis（对象有动态调整大小的集合时，不会进行栈上分配
 * @date 2025-12-09 07:34
 */
public class EscapeAnalysis {
    private static void alloc(){
        Person person = new Person();
        person.setUid("1001");
        person.setName("Tony");
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1024*1024*50; i++) {
            alloc();
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
}


@Data
class Person{
    private String uid;
    private String name;
    private int age;
    /**
     * 添加了list，系统就会取消栈上分配
     *
     * 因为JVM不知道 list 上能够分配多少内容。所以取消栈上分配
     */
    public ArrayList<String> list = new ArrayList<>();
}