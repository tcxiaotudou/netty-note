import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author fciasth
 * @desc 读文件
 * @date 2019/11/24
 */
public class NIOFileChannel02 {

    public static void main(String[] args) throws Exception {

        FileInputStream fileInputStream = new FileInputStream("/Users/tangchao/Study/temp/file01.txt");

        FileChannel fileChannel = fileInputStream.getChannel();

        // 可以只创建 file.length()大小的buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        fileChannel.read(byteBuffer);

        byteBuffer.flip();

        // 将byteBuffer的字节数据转成String

        System.out.println(new String(byteBuffer.array()));

        fileInputStream.close();
    }
}
