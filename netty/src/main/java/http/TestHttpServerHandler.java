package http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @author fciasth
 * @desc
 * @date 2019/12/1
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject msg) throws Exception {
         if (msg instanceof HttpRequest) {
             System.out.println("msg 类型：" + msg.getClass());
             System.out.println("客户端地址 " + channelHandlerContext.channel().remoteAddress());

             // pipeline 和 handler 每一次请求都是独立的一个
             HttpRequest httpRequest = (HttpRequest) msg;
             URI uri = new URI(httpRequest.uri());
             if (uri.getPath().endsWith("ico")) {
                 System.out.println("请求了ico，什么也不返回");
                 return;
             }

             ByteBuf byteBuf = Unpooled.copiedBuffer("hello, 我是服务器", CharsetUtil.UTF_8);

             FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
             response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
             response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());
             channelHandlerContext.writeAndFlush(response);
         }
    }
}
