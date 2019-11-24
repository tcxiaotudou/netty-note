import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @author fciasth
 * @desc 文件拷贝-图片拷贝
 * @date 2019/11/24
 */
public class NIOFileChannel04 {

    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("nio/src/main/resources/a.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("nio/src/main/resources/b.jpg");

        FileChannel sourceCh = fileInputStream.getChannel();
        FileChannel destCh = fileOutputStream.getChannel();

        destCh.transferFrom(sourceCh, 0, sourceCh.size());

        fileInputStream.close();
        fileOutputStream.close();
    }
}
