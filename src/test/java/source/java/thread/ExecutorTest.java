package source.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorTest {


    /*
    * ThreadPoolExecutor merupakan implementasi dari interface Executor dan ExecutorService
    * Jadi saat kita membuat ThreadPoolExecutor sebenarnya kita membuat Executor Service
    * Dan sebenarnya pembuatan Threadpool secara manual jarang dilakukan, kecuali pada kasus kita benar-benar butuh melakukan tuning Threadpool
    * Namun dalam kebanyakan kasus, kita jarang sekali membuat Threadpool secara manual
    * Rata-rata, biasanya untuk mengeksekusi Runnable, biasanya kita akan menggunakan Executor Service
     */


    /**
     * Karena ExecutorService adalah interface, jadi pembuatan object ExecutorService salah satu nya adalah menggunakan ThreadPoolExecutor
     * Namun ada yang lebih mudah, yaitu menggunakan class Executors
     * Sebenarnya implementasi Executors pun menggunakan ThreadPoolExecutor, hanya saja kita tidak perlu terlalu pusing melakukan pengaturan threadpool secara manual
     * Executor merupakan class utility untuk membantu kita membuat object ExecutorService secara mudah
     * Method yang tersedia dalam class Executor
        1. newFixedThreadPool(n)  -> Membuat threadpool dengan jumlah pool min dan max fix
        2. newSingleThreadExecutor()  -> Membuat threadpool dengan jumlah pool min dan max 1
        3. newCacheThreadPool()  ->  Membuat threadpool dengan jumlah thread bisa bertambah tidak terhingga
     */


    /*
    QUEUE EXECUTORS
    - Hati-hati ketika membuat ExecutorService menggunakan Executors class
    - Karena rata-rata Threadpool yang dibuat memiliki kapasitas queue tidak terbatas
    - Artinya jika terlalu banyak Runnable task di dalam queue, maka memori penyimpanan yang akan terpakai akan semakin besar
     */


    @Test
    void testNewFixedThreadPool() throws InterruptedException {
        var executor = Executors.newFixedThreadPool(10);//proses thread akan dikerjakan setaip 10 thread(langsung eksekusi 10)

        for (int i=0 ;i<100;i++){
            executor.execute(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("Runnable in Thread : "+Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);


    }

    @Test
    void testNewSingleThreadPool() throws InterruptedException {
        /*
        saat menggunakan method ini eksekusi thread dilakukan secara 1 persatu, berbeda dengan newFixedThreadPool yang dimana kita bisa menyetel berapa thread sekaligus yang dapat dikerjakan
         */

        var executor = Executors.newSingleThreadExecutor();//proses thread dilakukan 1 per 1
        Runnable runnable = () -> {
            try {
                Thread.sleep(1000);
                System.out.println("Runnable in Thread : "+Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };

        for (int i=0 ;i<10;i++){
            executor.execute(runnable);
        }


        /*
        NB = biasanya method awaitTermination() digunakan setelah memanggil method shutdown(), yang tujuannya untuk membantu dalam proses shutdown yang teratur
         */

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);//maksud dari parameter ini adalah 1 hari, jadi selama 1 hari akan menunggu semua thread selesai dikerjakan



    }


}
