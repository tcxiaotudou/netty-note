import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author fciasth
 * @desc 文件拷贝
 * @date 2019/11/24
 */
public class NIOFileChannel03 {

    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("nio/src/main/resources/1.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("nio/src/main/resources/2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (true) {
            int read = fileChannel01.read(byteBuffer);
            if (read == -1) {
                break;
            }
            byteBuffer.flip();
            fileChannel02.write(byteBuffer);
            byteBuffer.clear();
        }

        fileInputStream.close();
        fileOutputStream.close();
    }
}
