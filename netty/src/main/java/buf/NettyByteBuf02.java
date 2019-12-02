package buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * @author fciasth
 * @desc
 * @date 2019/12/2
 */
public class NettyByteBuf02 {

    public static void main(String[] args) {

        ByteBuf buffer = Unpooled.copiedBuffer("hello,world", Charset.forName("utf-8"));
        if (buffer.hasArray()) {

            byte[] array = buffer.array();
            System.out.println(new String(array, Charset.forName("utf-8")));

            System.out.println("byteBuf: " + buffer);

            System.out.println(buffer.arrayOffset());
            System.out.println(buffer.readerIndex());
            System.out.println(buffer.writerIndex());
            System.out.println(buffer.capacity());

            System.out.println(buffer.getByte(0));

            int len = buffer.readableBytes();

            System.out.println("len: " + len);

            for(int i = 0; i < len; i++) {
                System.out.println((char) buffer.getByte(i));
            }

            System.out.println(buffer.getCharSequence(0, 4, Charset.forName("utf-8")));
        }

    }
}
