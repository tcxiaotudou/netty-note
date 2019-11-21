import java.nio.IntBuffer;

/**
 * @author fciasth
 * @desc 举例说明Buffer的使用
 * @date 2019/11/21
 */
public class BasicBuffer {

    public static void main(String[] args) {
        // 创建一个Buffer，大小为5，即可存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 向Buffer存放数据
//        intBuffer.put(10);

        for (int i = 0;  i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        // 从Buffer中读取数据
        // 将Buffer转换，读写切换
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}

