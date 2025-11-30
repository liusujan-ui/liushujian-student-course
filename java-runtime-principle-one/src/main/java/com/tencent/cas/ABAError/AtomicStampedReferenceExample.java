package com.tencent.cas.ABAError;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 类名: AtomicStampedReferenceExample
 * 描述: 演示使用 AtomicStampedReference 解决 ABA 问题的示例。
 * 注意事项: 这种方法通过引入版本戳来检测 ABA 问题。
 */
public class AtomicStampedReferenceExample {

    /**
     * 字段: valueRef
     * 描述: 使用 AtomicStampedReference 来存储值及其版本戳。
     */
    private AtomicStampedReference<Integer> valueRef = new AtomicStampedReference<>(0, 0);

    /**
     * 方法: performABA
     * 描述: 模拟 ABA 问题并解决它。
     */
    public void performABA() {
        // 获取初始值和版本戳
        int[] stampHolder = new int[1];
        int initialValue = valueRef.get(stampHolder);
        int initialStamp = stampHolder[0];

        // 假设线程 T1 将值从 0 改为 1
        valueRef.compareAndSet(initialValue, 1, initialStamp, initialStamp + 1);

        // 线程 T2 将值从 1 改回 0
        valueRef.compareAndSet(1, 0, initialStamp + 1, initialStamp + 2);

        // 线程 T1 再次检查并尝试将值从 0 改为 2
        boolean success = valueRef.compareAndSet(initialValue, 2, initialStamp, initialStamp + 1);
        System.out.println("CAS with AtomicStampedReference successful: " + success);
    }

    public static void main(String[] args) {
        AtomicStampedReferenceExample example = new AtomicStampedReferenceExample();
        example.performABA();
    }
}
