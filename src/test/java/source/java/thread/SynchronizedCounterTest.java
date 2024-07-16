package source.java.thread;

import org.junit.jupiter.api.Test;

public class SynchronizedCounterTest {

    @Test
    void testCounter() throws InterruptedException{
        var counter = new SynchronizedCounter();
        Runnable runnable = () -> {
            for (int i = 0; i< 1000000; i++){
                counter.increment();
            }
        };
//        INI SECARA SEQUENTIAL(berurut), maka hasil akan benar yaitu dieksekusi 1 per 1 dulu baru dipanggil method get value yang ada di class CounterApp
//        runnable.run();
//        runnable.run();
//        runnable.run();


        /*
        Namun jika menggunakan thread proses eksekusinya akan fokus di 1 perulangan dulu baru lanjut ke perulangan yang lain, contoh dari ketiga thread akan berada di perulangan ke 100 harusnya thread 1 100, thread 2 102, thread 3 103
         */

        var thread1 = new Thread(runnable);
        var thread2 = new Thread(runnable);
        var thread3 = new Thread(runnable);

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();//jangan lupa memakai method join agar bisa dirunning bersama2 dalam 1 waktu
        thread2.join();
        thread3.join();

        System.out.println(counter.getValue());


    }


}
