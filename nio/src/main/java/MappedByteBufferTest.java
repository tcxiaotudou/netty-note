import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author fciasth
 * @desc MappedByteBuffer 可以让文件直接在内存中（堆外）修改，
 *       操作系统不需要再拷贝一次
 * @date 2019/11/24
 */
public class MappedByteBufferTest {

    public static void main(String[] args) throws Exception {

        RandomAccessFile randomAccessFile = new RandomAccessFile("nio/src/main/resources/1.txt", "rw");

        FileChannel fileChannel = randomAccessFile.getChannel();

        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) '9');

        randomAccessFile.close();
        System.out.println("修改成功");
    }
}
