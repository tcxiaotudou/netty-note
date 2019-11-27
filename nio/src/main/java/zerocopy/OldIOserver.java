package zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author fciasth
 * @desc
 * @date 2019/11/27
 */
public class OldIOserver {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(7777);
        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            byte[] byteArray = new byte[2048];
            while (true) {
                int read = dataInputStream.read(byteArray);
                if (read == -1) {
                    break;
                }
            }
        }
    }
}
