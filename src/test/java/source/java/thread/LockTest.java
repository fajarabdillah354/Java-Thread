package source.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {


    @Test
    void testCounter() throws InterruptedException{
        var counter = new CounterLock();
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


    @Test
    void testReadWriteLock() throws InterruptedException {
        /*
        -Kadang ada kondisi dimana kita ingin membedakan lock antara operasi update dan operasi get
        -Untuk kasus seperti ini, kita bisa membuat dua buah variable Lock
        -Namun, di Java disediakan cara yang lebih mudah, yaitu menggunakan interface ReadWriteLock
        -ReadWriteLock merupakan lock yang mendukung dua jenis operasi, read dan write
        -Implementasi dari interface ReadWriteLock adalah class ReentrantReadWriteLock

         */
        var counter = new CounterReadWriteLock();
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


    String message;
    @Test
    void testCondition() throws InterruptedException {

        //pada interface Condition kita bisa alternatif monitor (wait(), notify(), notifyAll()) dengan (wait(), signal(), signalAll()) dengan menggunakan method newCondition()


        /**
         * Pada Java modern saat ini, sangat disarankan menggunakan Condition dibanding monitor method
         * Condition memiliki method wait() untuk menunggu, signal() untuk mentrigger satu thread, dan signalAll() untuk mentrigger semua thread yang menunggu
         * Cara pembuatan Condition, kita bisa menggunakan method newCondition() milik Lock

         */



        Lock lock = new ReentrantLock();
        var condition = lock.newCondition();

        var thread1 = new Thread(() -> {
           try {
               lock.lock();
               condition.await();
               System.out.println(message);
           } catch (InterruptedException e) {
               e.printStackTrace();
           } finally {
                lock.unlock();
           }
        });

        var thread2 = new Thread(() -> {
           try {
               lock.lock();
               condition.await();
               System.out.println(message);
           } catch (InterruptedException e) {
               e.printStackTrace();
           } finally {
               lock.unlock();
           }
        });

        var thread3 = new Thread(() -> {
            //ini Contoh penggunaan signal()

//           try {
//               lock.lock();
//               condition.signal();//penggunaan signal() sama seperti notify
//               Thread.sleep(2000);
//               message = "hello fajar abdillah ahmad";
//           } catch (InterruptedException e) {
//               e.printStackTrace();
//           } finally {
//               lock.unlock();
//           }


            //ini Contoh penggunaan signalAll()
            try {
                lock.lock();
                condition.signalAll();//penggunaan signalAll() sama seperti notifyAll()
                Thread.sleep(2000);
                message = "hello fajar abdillah ahmad";
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }


        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();


    }
}
