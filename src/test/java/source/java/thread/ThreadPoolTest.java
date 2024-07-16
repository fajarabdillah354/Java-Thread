package source.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    @Test
    void testThreadPool() {
        //DIBAWAH INI ADALAH CONTOH PENGGUNAAN ThreadPool dalam java Thread
        /*
        Ada 3 komponen utama
        1. core pool adalah minimal thread yang dapat ketika ThreadPool terbuat
        2. max pool adalah maximal thread yang akan dibuat
        3. keep alive time yaitu berapa lama thread akan dihapus jika tidak bekerja
        4. queue yaitu antrian Thread

        cara kerja : task/tugas akan masuk ke dalam antrian setelah itu akan masuk ke dalam thread, dimana dalam thread ada core pool dan max pool
         */

        var minThread = 10;
        var maxThread = 50;
        var alive = 1;
        var aliveTime = TimeUnit.MINUTES;
        var queu = new ArrayBlockingQueue<Runnable>(100);

        var threadPool = new ThreadPoolExecutor(minThread,maxThread,alive,aliveTime,queu);
        System.out.println(threadPool);


    }


    @Test
    void testRunnablePool() throws InterruptedException {

        /*
        Untuk melakukan eksekusi Runnable, kita bisa menggunakan method execute() milik threadpool
        Secara otomatis data runnable akan dikirim ke queue threadpool untuk dieksekusi oleh thread yang terdapat di threadpool

         */

        var minThread = 10;
        var maxThread = 50;
        var alive = 1;
        var aliveTime = TimeUnit.MINUTES;
        var queu = new ArrayBlockingQueue<Runnable>(100);

        var threadPool = new ThreadPoolExecutor(minThread,maxThread,alive,aliveTime,queu);

        Runnable runnable = () -> {
            try {
                Thread.sleep(2000);
                System.out.println("Thread : "+ Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        threadPool.execute(runnable);
        System.out.println(threadPool.getMaximumPoolSize());//untuk mendapatkan maximum thread yang ada di dalam threadPool
        Thread.sleep(5000);


    }

    @Test
    void testShutdown() throws InterruptedException {

        /*
        - Jika kita sudah selesai menggunakan threadpool, dan tidak akan menggunakannya lagi, ada baiknya kita hentikan dan matikan ThreadPool nya
        - Caranya kita bisa menggunakan method shutdown() untuk menghentikan threadpool, jika ada pekerjaan yang belum dikerjakan, maka akan di ignore
        - Atau gunakan shutdownNow() untuk menghentikan threadpool, namun pekerjaan yang belum dikerjakan akan dikembalikan
        - Atau jika kita ingin menunggu sampai threadpool selesai, kita bisa gunakan awaitTermination()

         */


        var minThread = 50;
        var maxThread = 100;
        var alive = 1;
        var aliveTime = TimeUnit.MINUTES;
        var queu = new ArrayBlockingQueue<Runnable>(100);

        var threadPool = new ThreadPoolExecutor(minThread,maxThread,alive,aliveTime,queu);

        for (int i=0; i<100;i++){
            final var task = i;
            Runnable runnable = () -> {
                try {
                    Thread.sleep(5000);
                    System.out.println("Task ke -"+task+" dari Thread "+Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            threadPool.execute(runnable);
        }

        threadPool.shutdownNow();//menghentikan semuanya seketika
//        threadPool.shutdown();//menghentikan namun jika ada thread yang sedang berjalan ditunggu hingga selesai dulu
//        threadPool.awaitTermination(1, TimeUnit.DAYS);// menghentikan setelah program dijalankan



        /*
        Saat menjalankan thread kita wajib meng shutdown, karna misal kita telah menentukan waktu thread itu berjalan 1 days, ketika semua task sudah kelar dia akan tetep jalan hingga waktunya selesai, maka dari itu kita perlu method shutdown untuk memastikan ketika task sudah selesai di kerjakan segera menghentikan proses thread
         */


    }


    @Test
    void testRejectedHandler() throws InterruptedException {

        /*
        RejectedHandler adalah kondisi dimana queue penuh dan semua thread sedang dalam bekerja, contoh kita menset core threadnya 10, lalu maksimal max poolnya 100 tetapi capasitas atau antrian yang boleh tidak boleh lebih dari 10

        ketika terjadi itu kita bisa menggunakan implement class RejectedExecutionHandler untuk membuat thread reject, artinya yang dieksekusi hanya thread yang tidak melebih capasitas selebihnya akan di rejecct
         */


        var minThread = 10;
        var maxThread = 100;
        var alive = 1;
        var aliveTime = TimeUnit.MINUTES;
        var queu = new ArrayBlockingQueue<Runnable>(10);
        var rejectedHandler = new LogRejectedExecutionHandler();

        var threadPool = new ThreadPoolExecutor(minThread,maxThread,alive,aliveTime,queu, rejectedHandler);

        for (int i=0; i<1000;i++){
            final var task = i;
            Runnable runnable = () -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("Task ke- "+task+" di thread "+Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            threadPool.execute(runnable);
        }

        threadPool.awaitTermination(1,TimeUnit.DAYS);


    }

    public static class LogRejectedExecutionHandler implements RejectedExecutionHandler{//penggunakan RejectedExecutionHandler untuk menghandle reject pada thread


        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("thread "+ r+" is rejected");
        }
    }




}
