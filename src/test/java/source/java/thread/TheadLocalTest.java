package source.java.thread;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TheadLocalTest {

    /**
     * ThreadLocal memberi kita kemampuan untuk menyimpan data yang hanya bisa digunakan di thread tersebut
     * Tiap thread akan memiliki data yang berbeda dan tidak saling terhubung antar thread
     * Thread local memastikan variabel hanya menempel pada thread itu ketika threadnya berbeda maka variabelya berbeda juga
     * @throws InterruptedException
     */
    @Test
    void testThreadLocal() throws InterruptedException {

        final Random random = new Random();
        final UserLocal userLocal = new UserLocal();
        final var executor = Executors.newFixedThreadPool(10);//CORE THREAD

        for (int i = 0; i < 50; i++) {//tugas yang diberikan dengan core thread 10
            final var index = i;
            executor.execute(() -> {
                //intinya dia memanggil dirinya sendiri dalam 1 thread
                try {
                    userLocal.setLocal("user - "+index);//men set method local yang ada di class UserLocal
                    Thread.sleep(1000 + random.nextInt(5000));
                    userLocal.doAction();//memanggil threadnya sendiri
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }


        executor.awaitTermination(1, TimeUnit.HOURS);
    }
}
