package src.main.java;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fciasth
 * @desc 1、创建一个线程池
 *       2、如果有客户端连接，就创建一个线程，单独与之通讯
 * @date 2019/11/21
 */
public class BIOServer {

    public static void main(String[] args) throws Exception {

        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");
        while (true) {
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");
            newCachedThreadPool.execute(() -> {
                try {
                    handler(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void handler(Socket socket) throws IOException {
        byte[] bytes = new byte[1024];
        InputStream inputStream = socket.getInputStream();
        System.out.println("线程信息：id = " + Thread.currentThread().getId() + "名字 = " + Thread.currentThread().getName());
        while (true) {
            System.out.println("线程信息：id = " + Thread.currentThread().getId() + "名字 = " + Thread.currentThread().getName());
            int read = inputStream.read(bytes);
            if (read != -1) {
                System.out.println(new String(bytes, 0, read));
            }
        }
    }
}
