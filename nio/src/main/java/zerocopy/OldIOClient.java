package zerocopy;

import java.io.*;
import java.net.Socket;

/**
 * @author fciasth
 * @desc
 * @date 2019/11/27
 */
public class OldIOClient {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 7777);
        File file = new File("nio/src/main/resources/zh-Hans.lproj.zip");
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        FileInputStream fileInputStream = new FileInputStream(file);

        byte[] byteArray = new byte[2048];
        long readCount = 0;
        long total = 0;

        long startTime = System.currentTimeMillis();

        while (readCount != -1) {
            readCount = fileInputStream.read(byteArray);
            total += readCount;
            dataOutputStream.write(byteArray);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("总大小：" + total + " 耗时：" + (endTime -startTime));

        fileInputStream.close();
        dataOutputStream.close();
        socket.close();
    }
}
