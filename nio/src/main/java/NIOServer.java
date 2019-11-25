import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author fciasth
 * @desc NIO demo 服务端
 * @date 2019/11/25
 */
public class NIOServer {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 得到一个 Selector 对象
        Selector selector = Selector.open();

        serverSocketChannel.bind(new InetSocketAddress(6666));
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 将ServerSocketChannel注册到Selector上,关心事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 循环等待客户端连接
        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }
            // 如果返回>0，就获取相关的selectionKey集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();

                if (key.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接了");
                    // 将socketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel)key.channel();
                    ByteBuffer byteBuffer = (ByteBuffer)key.attachment();
                    socketChannel.read(byteBuffer);
                    System.out.println("from 客户端：" + new String(byteBuffer.array()));
                }

                keyIterator.remove();
            }
        }
    }
}
