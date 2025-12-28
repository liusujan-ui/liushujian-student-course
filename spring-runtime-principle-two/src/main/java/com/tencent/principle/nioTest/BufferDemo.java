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

    public static void main(String[] args) {
        //构建一个byte字节缓冲区，容量是4
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4);  // 堆外内存
//        ByteBuffer byteBuffer = ByteBuffer.allocate(4); // 堆内内存
        //默认写入模式，查看3个重要指标
        log.info("初始化：capacity容量：{}，position位置：{}，limit限制：{}", byteBuffer.capacity(), byteBuffer.position(), byteBuffer.limit());
        //写入2字节数据
        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) 2);
        byteBuffer.put((byte) 3);
        //再看数据
        log.info("写入3字节后：capacity容量：{}，position位置：{}，limit限制：{}", byteBuffer.capacity(), byteBuffer.position(), byteBuffer.limit());

        //转换为读取模式（不调用flip方法，也是可以读取数据的，但是position记录读取的位置不对）
        log.info("#########开始读取");
        byteBuffer.flip();
        byte a=byteBuffer.get();
        log.info("a={}",a);
        byte b=byteBuffer.get();
        log.info("b={}",b);
        log.info("读取2字节后：capacity容量：{}，position位置：{}，limit限制：{}", byteBuffer.capacity(), byteBuffer.position(), byteBuffer.limit());
        //继续写入3字节，此时读模式下，limit=3，position=2。继续写入只能覆盖写入一条数据
        //clear()方法清楚整个缓冲区。compact()方法仅清楚已阅读的数据。转为写入模式
        byteBuffer.compact();
        byteBuffer.put((byte) 3);
        byteBuffer.put((byte) 4);
        byteBuffer.put((byte) 5);
        log.info("最终的情况：capacity容量：{}，position位置：{}，limit限制：{}", byteBuffer.capacity(), byteBuffer.position(), byteBuffer.limit());

    }
}
