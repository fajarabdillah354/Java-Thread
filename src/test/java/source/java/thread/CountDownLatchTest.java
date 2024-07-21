package source.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {

    /**
     * CountDownLatch merupakan synchronizer yang digunakan untuk menunggu beberapa proses selesai, cara kerjanya mirip dengan Semaphore, yang membedakan adalah pada CountDownLatch, counter diawal sudah ditentukan
     * Setelah proses selesai, kita akan menurunkan counter
     * jika counter sudah bernilai 0, maka yang melakukan wait bisa lanjut berjalan
     * CountDownLatch cocok jika kita misal ingin menunggu beberapa proses yang berjalan secara asynchronous sampai semua proses selesai
     * @throws InterruptedException
     */
    @Test
    void testCountDown() throws InterruptedException {

        final var countDown = new CountDownLatch(5);
        final var executor = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 5; i++) {
            executor.execute(() -> {
                try {
                    System.out.println("Start Task");
                    Thread.sleep(2000);
                    System.out.println("Finished Task");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDown.countDown();
                }
            });
        }


        executor.execute(() -> {
            try {
                countDown.await();
                System.out.println("All Task Finished");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);



        //Kita bisa menunggu dengan menggunakan method await() yang terdapat di CountDownLatch




    }
}
