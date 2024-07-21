package source.java.thread;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class PhaserTest {

    /**
     * Direcomendasikan memakai cyclicbarrier dulu atau countdownlatch kecuali butuh yang dynamic baru kita butuh Phase()
     * karna dia bisa berubah2
     * @throws InterruptedException
     */

    @Test
    void CountDownLatch() throws InterruptedException {

        final var phaser = new Phaser();
        final var executor = Executors.newFixedThreadPool(20);


        phaser.bulkRegister(5);//kita akan registrasi 5 task
        phaser.bulkRegister(5);//artinya kita menambah incrementnya menjadi 10
        for (int i = 0; i < 10; i++) {//jika thread hanya sampai 4 maka program akan menunggu hingga tercapai batasnya yaitu 5
            executor.execute(() -> {

                try {
                    System.out.println("Start Task");
                    Thread.sleep(2000);
                    System.out.println("Finished Task");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    phaser.arrive();//akan menurunkan thread jika sudah dikerjakan
                }
            });
        }


        executor.execute(() -> {
            // NB : JIKA kita meregister 5 namun thread yang disediakan hanya 4 ketika menurunkan sampai ke 0 maka tidak akan sampai dia akan berhenti di 1 sehingga thread tidak akan pernah selesai karena syarat selesainya jika thread mencapai 0
            phaser.awaitAdvance(0);//batas turun thread yaitu 0
            System.out.println("All Finished");
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }


    @Test
    void testCyclicBarrier() throws InterruptedException {
        final var phaser = new Phaser();
        final var executor = Executors.newFixedThreadPool(20);

        phaser.bulkRegister(5);
        for (int i = 0; i < 5; i++) {//jika batas thread hanya 4 maka dia akan waiting terus
            executor.execute(() -> {
                phaser.arriveAndAwaitAdvance();
                System.out.println("Done Waiting ");
            });
        }
        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}
