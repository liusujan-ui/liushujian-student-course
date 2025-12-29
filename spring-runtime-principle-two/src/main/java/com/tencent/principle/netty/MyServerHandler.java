package com.tencent.principle.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自定义的Handler需要继承Netty规定好的HandlerAdapter
 * 才能被Netty框架所关联，有点类似SpringMVC的适配器模式
 **/
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当从对端（客户端）接收到数据时被调用。
     * Netty 会将接收到的数据封装成一个 {@link ByteBuf} 对象，并传递给此方法进行处理。
     *
     * @param ctx ChannelHandlerContext 对象，代表了 ChannelHandler 和 ChannelPipeline 之间的关联，
     *             可用于获取通道信息（如客户端地址）或进行后续操作（如写回数据）。
     * @param msg 接收到的消息对象。在大多数情况下，它是一个 {@link ByteBuf} 实例。
     * @throws Exception 如果在处理接收到的消息时发生任何错误
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取客户端发送过来的消息，并将其从 Object 类型转换为 ByteBuf 类型。
        // ByteBuf 是 Netty 的字节容器，相比 Java NIO 的 ByteBuffer 提供了更强大的功能和更灵活的操作。
        ByteBuf byteBuf = (ByteBuf) msg;

        // 打印从客户端接收到的消息。
        // ctx.channel().remoteAddress() 获取对端（客户端）的网络地址和端口。
        // byteBuf.toString(CharsetUtil.UTF_8) 将 ByteBuf 中的字节序列解码为 UTF-8 格式的字符串，以便正确显示中文等字符。
        System.out.println("收到客户端" + ctx.channel().remoteAddress() + "发送的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
    }


    /**
     * 当 Channel 的一次读操作完成时被调用。
     * 这个方法表明当前批次的数据已经全部从底层传输（如 TCP 缓冲区）读取完毕，并已通过 {@link #channelRead} 方法传递给了 ChannelPipeline。
     * 这是一个执行刷新（flush）操作的理想时机，可以将之前通过 {@link ChannelHandlerContext#write(Object)} 写入的数据一次性全部发送出去。
     *
     * @param ctx ChannelHandlerContext 对象，代表了 ChannelHandler 和 ChannelPipeline 之间的关联，
     *             用于执行网络操作，如写入和刷新数据。
     * @throws Exception 如果在发送消息过程中发生任何I/O错误
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 发送消息给客户端。
        // ctx.writeAndFlush(...) 是一个便捷方法，等同于先调用 ctx.write(...) 再调用 ctx.flush()。
        // 它会将数据写入到发送缓冲区，并立即刷新，确保数据被发送到网络中。
        // Unpooled.copiedBuffer(...) 是一个工具方法，用于将字符串（或字节数组）等数据包装成一个 ByteBuf 对象。
        // 这里创建了一个包含 UTF-8 编码字符串的 ByteBuf，作为响应发送给客户端。
        ctx.writeAndFlush(Unpooled.copiedBuffer("服务端已收到消息，并给你发送一个问号?", CharsetUtil.UTF_8));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //发生异常，关闭通道
        ctx.close();
    }
}