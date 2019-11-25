import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author fciasth
 * @desc NIO demo 客户端
 * @date 2019/11/25
 */
public class NIOClient {

    public static void main(String[] args) throws Exception {

        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.configureBlocking(false);

        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        if (!socketChannel.connect(inetSocketAddress)) {

            while (!socketChannel.finishConnect()) {
                System.out.println("因为连接需要事件，客户端不会阻塞，可以做其他工作");
            }
        }

        String str = "hello world";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        socketChannel.write(byteBuffer);

        System.in.read();
    }
}
