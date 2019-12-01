package simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import static io.netty.util.CharsetUtil.UTF_8;

/**
 * @author fciasth
 * @desc
 * @date 2019/11/28
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程：" + Thread.currentThread().getName());
        System.out.println("server ctx " + ctx);
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是：" + buf.toString(UTF_8));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());
        // ctx.channel.event.execute() 和 ctx.channel.event.schedule()
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete");
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端", UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
