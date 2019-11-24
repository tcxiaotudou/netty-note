import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author fciasth
 * @desc 写文件
 * @date 2019/11/24
 */
public class NIOFileChannel01 {

    public static void main(String[] args) throws Exception {

        String str = "hello,world";
        // 创建一个输出流 -> channel
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/tangchao/Study/temp/file01.txt");
        // 通过 FileOutputStream 获取对应的 Filechannel
        // 这个 filechannel 真实类型是 FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();

        // 创建一个缓冲区 ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 将 str 放入 bytebuffer
        byteBuffer.put(str.getBytes());

        // 对于bytebuffer进行反转
        byteBuffer.flip();

        // 将bytebuffer数据写入到filechannel
        fileChannel.write(byteBuffer);

        fileOutputStream.close();
    }
}
