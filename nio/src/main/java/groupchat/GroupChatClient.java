package groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author fciasth
 * @desc
 * @date 2019/11/26
 */
public class GroupChatClient {

    private Selector selector;

    private SocketChannel socketChannel;

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 8888;

    public GroupChatClient() throws IOException {
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    public void connect() throws IOException {
        if (!socketChannel.connect(new InetSocketAddress(HOST, PORT))) {
            System.out.println("连接失败");
        }
    }

    public void receive() throws IOException {
        if (selector.select() > 0) {
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey selectionKey = keyIterator.next();
                SocketChannel channel = (SocketChannel) selectionKey.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                channel.read(byteBuffer);
                System.out.println("from 客户端：" + channel.getRemoteAddress() + " " + new String(byteBuffer.array()));
                keyIterator.remove();
            }
        }
    }

    public void send() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
            socketChannel.write(byteBuffer);
        }
    }

    public static void main(String[] args) throws IOException {
        GroupChatClient groupChatClient = new GroupChatClient();
        while (true) {

        }
    }
}
