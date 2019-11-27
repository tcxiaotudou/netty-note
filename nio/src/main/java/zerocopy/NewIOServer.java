package zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author fciasth
 * @desc
 * @date 2019/11/27
 */
public class NewIOServer {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel =  ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7777);
        serverSocketChannel.socket().bind(inetSocketAddress);
        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            int read = 0;
            while (read != -1) {
                read = socketChannel.read(byteBuffer);
                byteBuffer.rewind();
            }
        }
    }
}
