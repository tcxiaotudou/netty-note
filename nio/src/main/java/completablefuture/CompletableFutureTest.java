package completablefuture;

import java.util.concurrent.CompletableFuture;

/**
 * @ClassName CompletableFutureTest
 * @Description TODO
 * @Date 2019/12/3 17:36
 * @Version 1.0
 */
public class CompletableFutureTest {

    public static void main(String[] args) throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int i = 1/0;
            return 100;
        });
        Integer result = future.join();
//        Integer result = future.get();
        System.out.println("result :" + result);
    }
}
