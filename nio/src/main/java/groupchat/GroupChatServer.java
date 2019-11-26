package groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author fciasth
 * @desc
 * @date 2019/11/26
 */
public class GroupChatServer {

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private static final int PORT = 8888;

    public GroupChatServer() throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
    }

    public void listen(ServerSocketChannel serverSocketChannel) throws IOException {
        serverSocketChannel.bind(new InetSocketAddress(PORT));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void sendInfoToOthers(String msg, SocketChannel socketChannel) throws IOException {
        Set<SelectionKey> keys = selector.keys();
        Iterator<SelectionKey> keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            SelectionKey key = keyIterator.next();
            Channel channel = key.channel();
            if (channel instanceof SocketChannel && channel != socketChannel) {
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                ((SocketChannel) channel).write(byteBuffer);
            }
        }
    }

    public void invoke() throws IOException {
        if (selector.select(1000) > 0) {
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey selectionKey = keyIterator.next();
                if (selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println(socketChannel.getRemoteAddress() + "上线了");
                }
                if (selectionKey.isReadable())  {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    socketChannel.read(byteBuffer);
                    String msg = new String(byteBuffer.array());
                    System.out.println("from 客户端：" + socketChannel.getRemoteAddress() +" message: " + msg);
                    sendInfoToOthers(msg, socketChannel);
                }
                keyIterator.remove();
            }
        } else {
            System.out.println("1s无客户端连接");
        }
    }

    public static void main(String[] args) throws IOException {
        GroupChatServer groupChatServer = new GroupChatServer();
        while (true) {
            groupChatServer.invoke();
        }
    }
}
