package source.java.thread;

import org.junit.jupiter.api.Test;

public class ThreadCommunicationTest {

    public String message = null;

    @Test
    void testWithLooping() throws InterruptedException {
        //dengan melakukan looping tidak optimal karna CPU haruss bekerja kerass untuk looping terus menerus

        //cara yang optimal menggunakan method wait() dan notify()

        var thread1 = new Thread(() ->{//di perulangan while ini program akan terus looping hingga nilai variable message di set, sehingga memakan banyak resource memori
           while (message == null){
               //wait
           }

            System.out.println(message);//setelah nilai dari message di set looping akan berhenti dan akan menampilkan output messagenya
        });


        var thread2 = new Thread(() -> {
           message = "fajar abdillah ahmad";
        });

        thread2.start();
        thread1.start();

        thread2.join();
        thread1.join();

    }


    @Test
    void testWaitNotify() throws InterruptedException {
        final Object lock = new Object();

        var thread1 = new Thread(() ->{
            synchronized (lock){
                try {
                    lock.wait();
                    System.out.println(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        /*
        simpelnya program terus menunggu dengan menggunakan method wait(), lalu ketika thread 2 di start(), variabel message akan bernilai dan thread2 akan mengirimkan informassi (notify) kepada thread1, setelah itu message bisa ditampilkan di thread1
         */

        var thread2 = new Thread(() -> {
            synchronized (lock){
                message = "fajarabdillah ahmad";
                //nofify() hanya memberi informasi pada 1 thread saja yang berjalan, untuk mengatasi semua thread menggunakan notifyAll()
                lock.notify();//jika tidak menggunakan method notify() maka program akan diam selamanya
            }

        });

        thread1.start();//thread yang duluan di panggil adalah thread yang mempunyai method wait()
        thread2.start();

        thread1.join();
        thread2.join();

    }


    @Test
    void testNotifyAll() throws InterruptedException {
        /*
        pada sebelumnya kita sudah mengetahui notify() hanya saja itu berlaku untuk 1 wait() saja, bagaimana jika banyak thread yang mempunyai wait() dan kita ingin menjalankan kode programnya


        cara yang bisa kita lakukan adalah dengan menggunakan method notifyAll()
         */
        final Object lock = new Object();

        var thread1 = new Thread(() ->{
            synchronized (lock){
                try {
                    lock.wait();
                    System.out.println(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

        var thread3 = new Thread(() ->{//thread3 berisi wait() juga
            synchronized (lock){
                try {
                    lock.wait();
                    System.out.println(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

        /*
        simpelnya program terus menunggu dengan menggunakan method wait(), lalu ketika thread 2 di start(), variabel message akan bernilai dan thread2 akan mengirimkan informassi kepada thread1, setelah itu message bisa ditampilkan di thread1
         */

        var thread2 = new Thread(() -> {
            synchronized (lock){
                message = "fajarabdillah ahmad";
                //nofifyAll() hanya memberi informasi pada banyak thread yang sedang berjalan
                lock.notifyAll();
            }

        });

        thread1.start();//thread yang duluan di panggil adalah thread yang mempunyai method wait()
        thread3.start();

        thread2.start();

        thread1.join();
        thread3.join();

        thread2.join();



    }
}
