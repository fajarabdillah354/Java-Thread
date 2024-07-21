package source.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ThreadLocalRandomTest {


    @Test
    void testRandom() throws InterruptedException {

        /**
         * Class ThreadLocalRandom telah menyedia object random yang bisa kita pakai pada tiap task di thread, ini mencegah terjadi kesalahan atau program yang lambat
         *
         */

        final var executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 50; i++) {//kita akan mengeksekusi 50 dengan core thread 10, yang masing thread sudah akan menggunakan random dengan bantuan class ThreadLocalRandom
            executor.execute(() -> {
                try {
                    Thread.sleep(2000);
                    var random = ThreadLocalRandom.current().nextInt(100);
                    System.out.println("random :  "+random);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }


        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void testRandomStream() throws InterruptedException {

        /**
         * Class ThreadLocalRandom telah menyedia object random yang bisa kita pakai pada tiap task di thread, ini mencegah terjadi kesalahan atau program yang lambat
         * Kita juga bisa memasukan random ke dalam stream, ini lebih canggih dari pada class Random biasa
         */

        final var executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 50; i++) {//kita akan mengeksekusi 50 dengan core thread 10, yang masing thread sudah akan menggunakan random dengan bantuan class ThreadLocalRandom
            executor.execute(() -> {
                try {
                    Thread.sleep(3000);
                    ThreadLocalRandom.current().ints(1000,0,500)
                            .forEach(System.out::println);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            });
        }


        executor.awaitTermination(1, TimeUnit.DAYS);
    }


}
