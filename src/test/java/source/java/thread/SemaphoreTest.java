package source.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {

    //semaphore test bertujuan untuk menjaga tugas yang di kerjakan di thread tidak terlalu banyak


    //Semua class yang ada di Synchorize sudah dijamin tidak ada race condition termasuk salah satunya Semaphore


    @Test
    void testSemaphore() throws InterruptedException {
        final Semaphore semaphore = new Semaphore(10);//artinya kita hanya boleh menjalankan 10 tugas dari thread yang diberikan.

        final var executor = Executors.newFixedThreadPool(100);//ini thread yaitu 100

        for(int i = 0; i<1000;i++){
            //baris dibawah ini menjaga membatasi pekerjaan yang di eksekusi, yaitu pada saat yang bersamaa cuma boleh ada 10 tugas yang jalan
            executor.execute(() -> {
                try {
                    semaphore.acquire();
                    Thread.sleep(1000);
                    System.out.println("Selesai");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            });
        }


        // NB Kita juga bisa menggunakan Semaphore untuk menjaga request yang terlalu banyak ke aplikasi lain


        executor.awaitTermination(1, TimeUnit.DAYS);

    }
}
