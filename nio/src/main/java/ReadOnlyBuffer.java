import java.nio.ByteBuffer;

/**
 * @author fciasth
 * @desc 只读buffer
 * @date 2019/11/24
 */
public class ReadOnlyBuffer {

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(64);

        for (int i = 0; i < 64; i++) {
            buffer.put((byte) i);
        }

        // 读取
        buffer.flip();

        // 得到一个只读buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }

        readOnlyBuffer.put((byte) 100); // ReadOnlyBufferException
    }
}
