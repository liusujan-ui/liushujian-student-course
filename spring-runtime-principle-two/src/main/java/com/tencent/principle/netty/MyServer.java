package com.tencent.principle.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyServer {
    /**
     * Netty 服务端的入口方法。
     * 该方法负责配置并启动一个基于 Netty 的服务器，监听指定的端口以接受客户端连接。
     * 它采用了经典的主从 Reactor 线程模型来处理高并发的网络连接。
     *
     * @param args 命令行参数（本例中未使用）
     * @throws Exception 如果在绑定端口或操作过程中发生任何I/O错误
     */
    public static void main(String[] args) throws Exception {
        // 创建两个线程组：bossGroup 和 workerGroup。
        // bossGroup: 主线程组（或称为“接收线程组”），专门负责接收新的客户端连接（accept）。
        // workerGroup: 从线程组（或称为“工作线程组”），专门负责处理已被 bossGroup 接收的连接的I/O操作（read/write）。
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 创建服务端的启动对象，用于配置各种服务端参数。
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 设置两个线程组，将主从线程组关联到 bootstrap。
            bootstrap.group(bossGroup, workerGroup)
                // 设置服务端通道实现类型。
                // NioServerSocketChannel 表示这是一个基于NIO的服务端Socket通道，用于监听和接受新连接。
                .channel(NioServerSocketChannel.class)
                // 设置线程队列得到连接个数。
                // SO_BACKLOG 表示服务端在请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。
                .option(ChannelOption.SO_BACKLOG, 128)
                // 设置保持活动连接状态。
                // childOption 用于配置被 accept 后的客户端连接通道。
                // SO_KEEPALIVE 表示开启TCP心跳机制，系统会定期检查连接是否存活。
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // 使用匿名内部类的形式初始化通道对象。
                // childHandler 用于为每个新的客户端连接（由 workerGroup 处理）配置处理器。
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 给 pipeline 管道设置处理器。
                        // socketChannel.pipeline() 是一个处理器链，所有与该连接相关的I/O事件都会按顺序通过这个链。
                        // 这里添加了一个自定义的服务端处理器 MyServerHandler 来处理业务逻辑（如读取客户端消息）。
                        socketChannel.pipeline().addLast(new MyServerHandler());
                    }
                });// 给workerGroup的EventLoop对应的管道设置处理器
            System.out.println("java技术爱好者的服务端已经准备就绪...");

            // 绑定端口号，启动服务端。
            // bind() 方法是异步的，它返回一个 ChannelFuture 对象。
            // .sync() 方法会使当前线程等待，直到绑定操作完成（成功或失败）。
            ChannelFuture channelFuture = bootstrap.bind(6666).sync();

            // 对关闭通道进行监听。
            // channelFuture.channel() 获取到代表服务端监听端口的 ServerSocketChannel。
            // .closeFuture() 返回一个 ChannelFuture，它会在通道关闭时完成。
            // .sync() 方法会使当前线程阻塞，直到服务端通道被关闭（例如，调用close()或JVM退出）。
            // 这可以防止 main 方法提前退出，导致服务端终止。
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅地关闭两个线程组，释放所有相关资源。
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}