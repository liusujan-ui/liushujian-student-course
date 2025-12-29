package com.tencent.principle.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyClient {
    /**
     * Netty 客户端的入口方法。
     * 该方法负责配置并启动一个基于 Netty 的客户端，用于连接到指定的服务器地址和端口。
     * 它使用了 NIO 事件驱动模型来处理网络I/O操作。
     *
     * @param args 命令行参数（本例中未使用）
     * @throws Exception 如果在连接或操作过程中发生任何I/O错误
     */
    public static void main(String[] args) throws Exception {
        // 创建 NioEventLoopGroup 对象。
        // 它是一组处理I/O操作的线程池，Netty使用它来处理事件，如接收新连接、读写数据等。
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            // 创建 bootstrap 对象，它是客户端的启动辅助类，用于配置各种客户端参数。
            Bootstrap bootstrap = new Bootstrap();
            // 设置线程组，将之前创建的 eventExecutors 赋给 bootstrap，
            // bootstrap 的所有I/O操作都将由这个线程组处理。
            bootstrap.group(eventExecutors)
                    // 设置客户端的通道实现类型。
                    // NioSocketChannel 表示这是一个基于NIO的客户端Socket通道。
                    .channel(NioSocketChannel.class)
                    // 使用匿名内部类初始化通道。
                    // 当一个新的连接被创建并被服务器接受后，这个处理器会被调用。
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 添加客户端通道的处理器。
                            // ch.pipeline() 是一个处理器链，所有事件都会按顺序通过这个链上的处理器。
                            // 这里添加了一个自定义的处理器 MyClientHandler 来处理业务逻辑。
                            ch.pipeline().addLast(new MyClientHandler());
                        }
                    });
            System.out.println("客户端准备就绪，随时可以起飞~");

            // 连接服务端。
            // connect() 方法是异步的，它返回一个 ChannelFuture 对象。
            // ChannelFuture 代表了一个尚未完成的I/O操作的结果。
            // .sync() 方法会使当前线程等待，直到连接操作完成（成功或失败）。
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6666).sync();

            // 对通道关闭进行监听。
            // channelFuture.channel() 获取到代表当前连接的 Channel 对象。
            // .closeFuture() 返回一个 ChannelFuture，它会在通道关闭时完成。
            // .sync() 方法会使当前线程阻塞，直到通道被关闭（例如，服务端断开连接或网络异常）。
            // 这可以防止 main 方法提前退出，导致客户端终止。
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 关闭线程组，释放所有相关资源。
            // shutdownGracefully() 会优雅地关闭线程组，它会先停止接受新任务，
            // 然后在指定的时间内完成正在执行的任务，最后强制关闭。
            eventExecutors.shutdownGracefully();
        }
    }


}