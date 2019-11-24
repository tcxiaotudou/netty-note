import java.nio.ByteBuffer;

/**
 * @author fciasth
 * @desc
 * @date 2019/11/24
 */
public class NIOByteBufferPutGet {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);

        buffer.putInt(100);
        buffer.putLong(9L);
        buffer.putChar('s');
        buffer.putShort((short)4);

        // 取出
        buffer.flip();

        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
    }
}
