package source.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierTest {

    @Test
    void testCyclicBarrier() throws InterruptedException {

        //disini kita akan menunggu thread hingga sesuai dengan yang kita tentukan di Constructor CyclicBarrier

        final CyclicBarrier cyclicBarrier = new CyclicBarrier(5);//kita akan mulai jika jumlah threadnya mencapai 5 jika belum mencapai 5 maka thread tidak akan berjalan

        final var executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 5; i++) {//karna jumlah threadnya 5 maka sesuai dengan parameter CyclicBarriernya yaitu proses akan berjlaan minimal jika threadnya 5, jika kita udah menjadi 4 maka program tidak akan jalan karena CyclicBarrier menunggu hingga thread terisi 5 baru berjalan
            executor.execute(() -> {
                try {
                    System.out.println("Waiting thread");
                    cyclicBarrier.await();
                    System.out.println("Thread Done");
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });

        }
        executor.awaitTermination(1, TimeUnit.DAYS);

    }
}
