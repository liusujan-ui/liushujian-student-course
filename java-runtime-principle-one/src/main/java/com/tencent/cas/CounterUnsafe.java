package com.tencent.cas;

import com.tencent.util.Counter;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 观自在
 * @description
 * @date 2025-11-30 13:32
 */
/**
 * 类名: CounterUnsafe
 * 描述: 提供基于 Unsafe 类的原子操作来增加计数器的值。
 * 注意事项: 使用 Unsafe 类进行原子操作时，需要谨慎处理以避免潜在的安全和性能问题。
 */
public class CounterUnsafe {

    /**
     * 静态变量: unsafe
     * 描述: Unsafe 类的单例实例，用于执行低级别的原子操作。
     */
    private static Unsafe unsafe;

    /**
     * 静态变量: valueOffset
     * 描述: CounterUnsafe 类中 count 字段的内存偏移量，用于 Unsafe 操作。
     */
    private static long valueOffset;

    /**
     * 字段: count
     * 描述: 计数器的值，使用 volatile 关键字确保可见性。
     */
    private volatile int count;

    public static void main(String[] args) throws Exception{
        test();
    }

    /**
     * 静态初始化块
     * 描述: 初始化 Unsafe 实例和 count 字段的偏移量。
     * 注意事项: 如果初始化过程中发生异常，会打印堆栈跟踪信息。
     */
    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe"); // 获取 Unsafe 类的 theUnsafe 字段
            theUnsafe.setAccessible(true); // 设置该字段可访问
            unsafe = (Unsafe) theUnsafe.get(null); // 获取 Unsafe 实例

            Field field = CounterUnsafe.class.getDeclaredField("count"); // 获取 CounterUnsafe 类的 count 字段
            valueOffset = unsafe.objectFieldOffset(field); // 获取 count 字段的内存偏移量
        } catch (Exception e) {
            e.printStackTrace(); // 打印堆栈跟踪信息，便于调试
        }
    }


    public void inc() { // 自旋的问题？ -- 性能损耗 --计算机 --内存的减小 --cpu占用
        for(;;) { // 无限循环，直到条件满足
            int current = unsafe.getIntVolatile(this, valueOffset); // 获取当前 count 字段的值
            boolean b = unsafe.compareAndSwapInt(this, valueOffset, current, current + 1); // 尝试原子性地将 count 增加 1
            if(b) { // 如果 compareAndSwapInt 成功
                break; // 退出循环
            }
            // 一旦失败了，重新试。只到成功了，再结束
        }
    }



    public static void test() throws InterruptedException {
        final CounterUnsafe counter=new CounterUnsafe();

        for(int i=0;i<6;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        counter.inc();
                    }
                    System.out.println("done...");
                }
            }).start();
        }

        Thread.sleep(10000L);
        System.out.println(counter.count);
    }
}
