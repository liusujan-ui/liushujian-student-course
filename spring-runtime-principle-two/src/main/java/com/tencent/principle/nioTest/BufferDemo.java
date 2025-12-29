package com.tencent.principle.nioTest;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

/**
 * @author 观自在
 * @description
 * @date 2025-12-28 10:31
 */
@Slf4j
public class BufferDemo {

    /**
     * 演示 Java NIO ByteBuffer 的核心用法，包括创建、写入、模式切换、读取和压缩操作。
     * ByteBuffer 是一个用于在通道（Channel）和缓冲区之间进行高效数据读写的字节容器。
     */
    public static void main(String[] args) {
        // 构建一个 byte 字节缓冲区，指定容量为 4 字节。
        // ByteBuffer.allocateDirect(4) 创建一个直接缓冲区（堆外内存）。
        // 直接缓冲区的内存分配在 JVM 堆之外，可以减少一次数据拷贝，从而提高 I/O 效率，但分配和回收成本较高。
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4);  // 堆外内存
        // ByteBuffer.allocate(4) 会创建一个非直接缓冲区（堆内内存），它在 JVM 堆上分配，便于管理但 I/O 时可能涉及额外的拷贝。
        // ByteBuffer byteBuffer = ByteBuffer.allocate(4); // 堆内内存

        // 初始化后，ByteBuffer 处于写入模式。
        // 查看三个核心指标：
        // capacity（容量）：缓冲区能够容纳的数据元素的最大数量，创建后不可改变。此处为 4。
        // position（位置）：下一个要被读或写的元素的索引。初始为 0。
        // limit（限制）：第一个不应该被读或写的元素的索引。写入模式下，limit 等于 capacity。此处为 4。
        log.info("初始化：capacity容量：{}，position位置：{}，limit限制：{}", byteBuffer.capacity(), byteBuffer.position(), byteBuffer.limit());

        // 向缓冲区写入 2 字节数据。每写入一个字节，position 会向后移动一位。
        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) 2);
        byteBuffer.put((byte) 3); // 实际上这里写入了3个字节

        // 再次查看核心指标：
        // capacity 保持不变，仍为 4。
        // position 因为写入了3个字节，所以变为 3，指向下一个可写入的位置。
        // limit 在写入模式下保持不变，仍为 4。
        log.info("写入3字节后：capacity容量：{}，position位置：{}，limit限制：{}", byteBuffer.capacity(), byteBuffer.position(), byteBuffer.limit());

        // 转换为读取模式。
        // flip() 方法是切换模式的关键，它会：
        // 1. 将 limit 设置为当前 position 的值（即已写入数据的末尾）。
        // 2. 将 position 重置为 0，表示从缓冲区开头开始读取。
        // 如果不调用 flip() 而直接读取，position 会从3开始，导致读取到错误的数据或抛出 BufferUnderflowException。
        log.info("#########开始读取");
        byteBuffer.flip();

        // 从缓冲区中读取数据。每读取一个字节，position 会向后移动一位。
        byte a = byteBuffer.get();
        log.info("a={}", a); // 读取 position=0 处的字节
        byte b = byteBuffer.get();
        log.info("b={}", b); // 读取 position=1 处的字节

        // 查看读取2字节后的状态：
        // capacity 保持不变，仍为 4。
        // position 因为读取了2个字节，所以变为 2。
        // limit 在 flip() 时被设置为 3，表示可读数据的边界。
        log.info("读取2字节后：capacity容量：{}，position位置：{}，limit限制：{}", byteBuffer.capacity(), byteBuffer.position(), byteBuffer.limit());

        // 尝试继续写入3字节数据。
        // 此时，缓冲区仍处于读取模式，position=2, limit=3。
        // 直接 put() 会导致在 position=2 的位置写入，覆盖掉未读的数据（这里是原 position=2 的数据，值为3）。
        // 为了在不丢失未读数据的情况下重新切换到写入模式，通常使用 compact() 方法。
        // clear() 方法会清空整个缓冲区（position=0, limit=capacity），丢失所有数据。
        // compact() 方法则将所有未读的数据（从 position 到 limit）移动到缓冲区的开头，
        // 然后将 position 设置为未读数据之后的位置，limit 设置为 capacity，从而为后续写入做好准备。
        byteBuffer.compact(); // 执行 compact 后，position 将变为 2（因为有一个字节未读），limit 变为 4。

        // 现在缓冲区处于写入模式，可以从 position=2 的位置开始写入新数据。
        byteBuffer.put((byte) 3); // 写入到索引 2
        byteBuffer.put((byte) 4); // 写入到索引 3
        byteBuffer.put((byte) 5); // 触发 BufferOverflowException，因为缓冲区已满（容量为4）

        // （注意：上面的代码会因为缓冲区溢出而抛出异常，这是一个演示 compact 后写入边界的例子）
        // 如果只写入两个字节，缓冲区状态如下：
        log.info("最终的情况：capacity容量：{}，position位置：{}，limit限制：{}", byteBuffer.capacity(), byteBuffer.position(), byteBuffer.limit());
    }

}
