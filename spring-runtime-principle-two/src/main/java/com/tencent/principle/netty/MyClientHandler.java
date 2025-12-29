package com.tencent.principle.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class MyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当 Channel（通道）处于活动状态时被调用。
     * 这通常意味着客户端到服务器的连接已成功建立。
     * 这是一个发送初始消息或执行启动后操作的绝佳时机。
     *
     * @param ctx ChannelHandlerContext 对象，代表了 ChannelHandler 和 ChannelPipeline 之间的关联，
     *             可以用于进行网络I/O操作，如写入数据、刷新流等。
     * @throws Exception 如果在发送消息时发生任何I/O错误
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 发送消息到服务端。
        // ctx.writeAndFlush() 是一个组合操作，它会将数据写入并发送出去。
        // Unpooled.copiedBuffer() 是一个工具方法，用于将字符串转换为 ByteBuf（Netty的字节容器）。
        // CharsetUtil.UTF_8 指定了字符串编码为UTF-8格式，确保中文字符能正确传输。
        ctx.writeAndFlush(Unpooled.copiedBuffer("歪比巴卜~茉莉~Are you good~马来西亚~", CharsetUtil.UTF_8));
    }


    /**
     * 当从对端（服务端）接收到数据时被调用。
     * Netty 会将接收到的数据封装成一个 {@link ByteBuf} 对象，并传递给此方法。
     *
     * @param ctx ChannelHandlerContext 对象，代表了 ChannelHandler 和 ChannelPipeline 之间的关联，
     *             可用于获取通道信息或进行后续操作。
     * @param msg 接收到的消息对象。在大多数情况下，它是一个 {@link ByteBuf} 实例。
     * @throws Exception 如果在处理接收到的消息时发生任何错误
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 接收服务端发送过来的消息，并将其从 Object 类型转换为 ByteBuf 类型。
        // ByteBuf 是 Netty 的字节容器，比 Java NIO 的 ByteBuffer 更强大、更灵活。
        ByteBuf byteBuf = (ByteBuf) msg;

        // 打印从服务端接收到的消息。
        // ctx.channel().remoteAddress() 获取对端（服务端）的地址。
        // byteBuf.toString(CharsetUtil.UTF_8) 将 ByteBuf 中的字节解码为 UTF-8 格式的字符串。
        System.out.println("收到服务端" + ctx.channel().remoteAddress() + "的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
    }

}