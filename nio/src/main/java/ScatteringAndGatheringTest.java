import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author fciasth
 * @desc
 * Scattering: 将数据写入到buffer时，可以采用buffer数组，依次写入   [分散]
 * Gathering:  从buffer读取数据时，可以采用buffer数组，依次读取    [聚集]
 * @date 2019/11/24
 */
public class ScatteringAndGatheringTest {

    public static void main(String[] args) throws Exception {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

//        SocketChannel socketChannel =
        serverSocketChannel.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];

        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        // 等客户端连接（Telnet）
        SocketChannel socketChannel = serverSocketChannel.accept();
        long msgLength = 8; // 假定从客户端接收8个字节
        while (true) {
            int byteRead = 0;
            while (byteRead < msgLength) {
                long read = socketChannel.read(byteBuffers);
                byteRead += read;
                System.out.println("byteRead = " + byteRead);
                // 查看当前buffer的position和limit
                Arrays.stream(byteBuffers).forEach(byteBuffer -> {
                    System.out.println("position: " + byteBuffer.position()
                    + " limit " + byteBuffer.limit());
                });

            }
            // 将所有buffer进行反转
            Arrays.stream(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());

            // 将数据显示到客户端
            long byteWrite = 0;
            while (byteWrite < msgLength) {
                long write = socketChannel.write(byteBuffers);
                byteWrite += write;
            }

            // 将所有的buffer进行clear
            Arrays.stream(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());

            System.out.println("byteRead: = " + byteRead + " byteWrite " + byteWrite
            + " msgLength " + msgLength);
        }
    }
}
