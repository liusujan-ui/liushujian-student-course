package com.tencent.cas.ABAError;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 类名: AtomicIntegerExample
 * 描述: 演示使用 AtomicInteger 发生 ABA 问题的示例。
 * 注意事项: 这种方法不能检测到 ABA 问题。
 */
public class AtomicIntegerExample {

    /**
     * 字段: value
     * 描述: 使用 AtomicInteger 来存储值。
     */
    private AtomicInteger value = new AtomicInteger(0);

    /**
     * 方法: performABA
     * 描述: 模拟 ABA 问题。
     */
    public void performABA() {
        // 初始值为 0
        int initialValue = value.get();

        // 假设线程 T1 将值从 0 改为 1
        value.compareAndSet(initialValue, 1);

        // 线程 T2 将值从 1 改回 0
        value.compareAndSet(1, 0);

        System.out.println(initialValue);

        // 线程 T1 再次检查并尝试将值从 0 改为 2
        boolean success = value.compareAndSet(initialValue, 2);
        System.out.println("CAS with AtomicInteger successful: " + success);
    }

    public static void main(String[] args) {
        AtomicIntegerExample example = new AtomicIntegerExample();
        example.performABA();
    }
}
