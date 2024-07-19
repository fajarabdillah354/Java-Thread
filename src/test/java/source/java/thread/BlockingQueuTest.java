package source.java.thread;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.concurrent.*;

public class BlockingQueuTest {

    /** Jenis Concurent Collection
     secara garis besar concurrent di java terbagi menjadi 2 interface
     * BlockingQueue, merupakan turunan dari Queue, dan dikhususkan untuk tipe collection FIFO (first in first out), seperti yang sebelumnya sudah kita bahas sekilas di ThreadPool Queue
     * ConcurrentMap, merupakan turunan dari Map, dan dikhususkan untuk Map yang thread safe dibanding implementasi Map di Java Collection
     */


    @Test
    void testArrayBlocking() throws InterruptedException {

        //pada arrayBlocking ukuran fix tidak unlimited
        final var arrayQueu = new ArrayBlockingQueue<String>(5);//kasitasnya adalah 5, dimana data akan diproses per 5
        final var executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    arrayQueu.put("Data");//masukan data ke threadpool
                    System.out.println("Finished Data");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        //KONSEP dari ini ada threadpool yang masukin data lalu ada threadpool yang mengambil datanya dengan method take()

        executor.execute(() -> {
            while (true){
                try {
                    Thread.sleep(2000);
                    var result = arrayQueu.take();//threadpool yang bertugas mengambil data
                    System.out.println("Data Receive : "+result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);

    }


    @Test
    void testLinkedBlocking() throws InterruptedException {

        //pada LinkedBlocking ukurang akan bebas sesuai banyak thread yang dibutuhkan
        final var linkedQueue = new LinkedBlockingDeque<String>();
        final var executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    linkedQueue.put("Data");//masukan data ke threadpool
                    System.out.println("Finished Data");//program akan menyelesaikan Finished Data dahulu hingga batas thread yaitu 10, setelah itu baru akan mengambil data
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        //KONSEP dari ini ada threadpool yang masukin data lalu ada threadpool yang mengambil datanya dengan method take()

        executor.execute(() -> {
            while (true){
                try {
                    Thread.sleep(2000);
                    var result = linkedQueue.take();//threadpool yang bertugas mengambil data setiap 2 detik hingga batas thread yaitu 10
                    System.out.println("Data Receive : "+result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);

        //Cation = jika menggunakan ini hati2 karna bisa memakan merori bebas dan dapat menyebab memori overflow hingga crach

    }


    @Test
    void testPriorityBlockingQueue() throws InterruptedException {

        //pada PriorityBlocking akan berurut berdasarkan prioritas, kita juga dapat menambahkan parameter untuk mencostum prioritasnya contoh menambahkan Comparator.reverseOrder()
        final var priorityQueue = new PriorityBlockingQueue<Integer>(10, Comparator.reverseOrder());
        final var executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            var index = i;
            executor.execute(() -> {
                priorityQueue.put(index);//masukan data ke threadpool
                System.out.println("Finished Data");
            });
        }


        executor.execute(() -> {
            while (true){
                try {
                    Thread.sleep(2000);
                    var result = priorityQueue.take();//akan mengambil nilai dari index lalu akan menampilkan dari yang besar ke yang kecil
                    System.out.println("Data Receive : "+result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);


    }

    @Test
    void testDelayedQueue() throws InterruptedException {

        // tipe data Delayed, dimana data tidak bisa diambil sebelum waktu delay yang telah ditentukan

        final var delayedQueu = new DelayQueue<ScheduledFuture<String>>();
        final var executor = Executors.newFixedThreadPool(20);
        final var scheduleExecutor = Executors.newScheduledThreadPool(10);

        for (int i = 0; i < 10; i++) {
            var index = i;
            delayedQueu.put(scheduleExecutor.schedule(() -> "Data" + index, i, TimeUnit.SECONDS));
        }


        executor.execute(() -> {
            while (true){
                try {
                    var result = delayedQueu.take();//akan mengambil nilai dari index lalu akan menampilkan dari yang besar ke yang kecil
                    System.out.println("Data Receive : "+result.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);


    }


    @Test
    void testSynchronousQueue() throws InterruptedException {

        //jika mau ngirim data nunggu ada yang narik data, jadi akan menunggu hingga ada yang narik data baru data akan dimasukin


        final var queue = new SynchronousQueue<String>();
        final var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            final var index = i;
            executor.execute(() -> {
                try {
                    queue.put("Data "+index);//masukan data ke threadpool
                    System.out.println("Mengirim Data : "+index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        //hasil mungkin tidak beraturan karna thread akan dirunning secara acak cepat cepatan

        executor.execute(() -> {
            while (true){
                try {
                    Thread.sleep(2000);
                    var result = queue.take();//akan mengambil nilai dari index lalu akan menampilkan dari yang besar ke yang kecil
                    System.out.println("Receive Data : "+result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);

    }



    @Test
    void testBlockingDequeue() throws InterruptedException {

        //dengan menggunakan blocking Dequeue kita bisa secara bebas menentukan mengambil data dari depan atau dari belakang

        /** Method blocking
          untuk mengirim data ke paling belakang dan mengambil yang paling belakang bisa menggunakan method putLast() dan takeLast()
          untuk mengirim data ke paling depan dan mengambil yang paling depan bisa menggunakan method putFirst() dan takeFirst()
         @implNote
         */


        final var queue = new LinkedBlockingDeque<String>();
        final var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            final var index = i;
                try {
                    queue.putLast("Data "+index);//masukan data ke threadpool ke yang paling belakang
                    System.out.println("Mengirim Data : "+index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }

        //hasil mungkin tidak beraturan karna thread akan dirunning secara acak cepat cepatan

        executor.execute(() -> {
            while (true){
                try {
                    Thread.sleep(2000);
                    var result = queue.takeLast();//akan mengambil data yang paling belakang
                    System.out.println("Receive Data : "+result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }


    @Test
    void testTransferQueue() throws InterruptedException {

        // transfer akan selesai jika ada data yang menerima,baru tranfers akan selesai




        final var queue = new LinkedTransferQueue<String>();
        final var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            final var index = i;
            executor.execute(() -> {
                try {
                    queue.transfer("Data "+index);//masukan data ke threadpool ke yang paling belakang
                    System.out.println("Mengirim Data : "+index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });


        }

        //hasil mungkin tidak beraturan karna thread akan dirunning secara acak cepat cepatan

        executor.execute(() -> {
            while (true){
                try {
                    Thread.sleep(2000);
                    var result = queue.take();//akan mengambil data yang paling belakang
                    System.out.println("Receive Data : "+result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }




}
