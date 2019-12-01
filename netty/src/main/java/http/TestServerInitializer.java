package http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author fciasth
 * @desc
 * @date 2019/12/1
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast("myHttpServerCodec", new HttpServerCodec())
                .addLast("myTestHttpServerHandler", new TestHttpServerHandler());
        System.out.println("ok ...");
    }
}
