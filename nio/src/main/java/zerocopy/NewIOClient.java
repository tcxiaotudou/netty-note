package zerocopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author fciasth
 * @desc
 * @date 2019/11/27
 */
public class NewIOClient {

    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 7777);
        socketChannel.connect(inetSocketAddress);
        FileInputStream fileInputStream = new FileInputStream(new File("nio/src/main/resources/zh-Hans.lproj.zip"));
        FileChannel fileChannel = fileInputStream.getChannel();

        long startTime = System.currentTimeMillis();
        long total = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        long endTime = System.currentTimeMillis();
        System.out.println("总大小：" + total + " 耗时：" + (endTime -startTime));
        socketChannel.close();
    }
}
