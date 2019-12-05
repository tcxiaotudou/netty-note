package websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author fciasth
 * @desc
 * @date 2019/12/5
 */
public class MyServer {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new HttpServerCodec())
                                    //是以块方式写，添加ChunkedWriteHandler处理器
                                    .addLast(new ChunkedWriteHandler())
                                    /*
                                    说明
                                    1. http数据在传输过程中是分段, HttpObjectAggregator ，就是可以将多个段聚合
                                    2. 这就就是为什么，当浏览器发送大量数据时，就会发出多次http请求
                                     */
                                    .addLast(new HttpObjectAggregator(8192))
                                    /*
                                    说明
                                    1. 对应websocket ，它的数据是以 帧(frame) 形式传递
                                    2. 可以看到WebSocketFrame 下面有六个子类
                                    3. 浏览器请求时 ws://localhost:7000/hello 表示请求的uri
                                    4. WebSocketServerProtocolHandler 核心功能是将 http协议升级为 ws协议 , 保持长连接
                                    5. 是通过一个 状态码 101
                                     */
                                    .addLast(new WebSocketServerProtocolHandler("/hello"))
                                    .addLast(new MyTextWebSocketFrameHandler());
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
