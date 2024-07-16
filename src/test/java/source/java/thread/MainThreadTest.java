package source.java.thread;

import org.junit.jupiter.api.Test;

public class MainThreadTest {

    //DEFAULT Thread
    @Test
    void testMainThread() {
        String name = Thread.currentThread().getName();

        System.out.println(name);

    }


    //Runnable Thread
    @Test
    void testRunnable() {

        /**
         * untuk memberikan pekerjaan ke dalam Thread kita menggunakan Interface Runnable, lalu untu memulainya menggunakan method start()
         * untuk membuat Runnable kita harus menggunakan annoymous function atau lambda
         */
        Runnable runnable = () -> {
            System.out.println("hello from thread : "+Thread.currentThread().getName());
        };

        //Thread diawali dengan ke-0 lalu dilanjut seterusnya tergantung Thread yang kita pakai
        Thread iniThread = new Thread(runnable);
        iniThread.start();

        System.out.println("program selesai");

        //saat perintah atas dijalankan akan tampil duluan, ini bukan berarti threadnya tidak berjalan namun karena jalan bareng2 hanya saja thread lebih lama untuk di eksekusi
    }


    //Sleep Thread
    @Test
    void ThreadSleep() throws InterruptedException{
        /*
        * saat menggunakan Thread.sleep() kita wajib melakukan try catch dengn tipe error InterupptedException
        * hello program thread dibawah akan berjalan setelah menunggu waktu 2 second,
        * pertama kali program yang tampil adalah "program selesai"
        * saat selesai kita perlu menambahkan Thread kembali sebelum unit test selesai
         */
        Runnable runnable = () ->{
            try {
                Thread.sleep(2000);//method sleep() untuk membuat program berhenti beberap waktu,namun tetap bisa merunning program lain
                System.out.println("hello program thread : "+Thread.currentThread().getName());//output ini akan tampil setelah 2 second
            }catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        System.out.println("program selesai");
        Thread.sleep(3000);//kita harus menambahkan lagi sleep ini untuk menunggu proses diatas yang tertunda 2 detik, jika tidak membuat Thread lagi program keburu selesai dari unit test

    }



    //Join Thread
    @Test
    void ThreadSleepJoin() throws InterruptedException{
        /* kita bisa menunggu thread yang kita buat hingga selesai dengan method join()
         * untuk mempermudah gunakan method join(), sehingga akan menunggu hingga threadnya selesai dulu
         */
        Runnable runnable = () ->{
            try {
                Thread.sleep(2000);//method sleep() untuk membuat program berhenti beberap waktu,namun tetap bisa merunning program lain
                System.out.println("hello program thread : "+Thread.currentThread().getName());//output ini akan tampil setelah 2 second
            }catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        System.out.println("menunggu thread selesai");
        thread.join();//dengan menggunakan join proses akan ditunggu hingga selesai baru akan dilanjut program yang ada dibawah method join
        System.out.println("program selesai");

    }


    //Interupt Thread
    //INI CONTOH INTERUPT YANG SALAH
    @Test
    void testInteruptFalse() throws InterruptedException{

        Runnable runnable = () -> {
            for (int i = 1; i<=10 ; i++){
                System.out.println("Thread : " + i);
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        /**
         * ini adalah contoh interupt yang salah karena program masih terus berjalan meskipun ada interupt terjadi
         * langkah yang benar adalah menghentikan program jika terdapat interupt
         */

        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();//kita melakukan interupt saat second yang ke 5
        System.out.println("program Thread");
        thread.join();
        System.out.println("program Thread selesai");


    }


    //Interupt Thread YANG BENAR
    @Test
    void testInteruptCorrect() throws InterruptedException{

        /**
         * ketika ada interupt usahakan berhentikan program
         */
        Runnable runnable = () -> {
            for (int i = 1; i<=10 ; i++){
                //kita juga bisa menambahkan interupt secara manual dengan Thread.interrupted()
                if (Thread.interrupted()){
                    return;
                }
                System.out.println("Thread : " + i);
                try {
                    Thread.sleep(1000);//kita cek 1 persatu program dengan Thread.sleep() jika ada interupt diberhentikan
                }catch (InterruptedException e){
                    return;// dengan menggunakan return atau break perulangan yang sedang terjadi berhenti
                }
            }
        };
        /**
         * ini adalah contoh interupt yang benar karena program akan langsung dihentikan

         */

        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();//kita melakukan interupt saat second yang ke 5
        System.out.println("terdapat interupt Thread");
        thread.join();
        System.out.println("program Thread selesai");

    }


    //Name Thread
    @Test
    void testThreadName() {
        /*
        saat debugging mungkin kita membutuhkan nama thread yang sesuai dengan project kita, kita bisa mengubah nama thread menjadi apa yang kita inginkan. ini berguna untuk proses debug. kita bisa menggunakan method setName(name)
         */

        var fajar = new Thread(() -> {
            System.out.println("Run in Thread : "+Thread.currentThread().getName());
        });

        //sebelum kita start kita bisa mengubah nama threadnya
        fajar.setName("fajar");
        fajar.start();
    }



    //State Thread
    @Test
    void testThreadState() throws InterruptedException{
        /*
        state adalah isi informasi dari thread yang meliputi
        1. NEW -> state baru
        2. Runnable -> thread masuk ke tahap runnable
        3. Terminated -> thread telah selesai dijalankan
         */

        var fajar = new Thread(() -> {//NEW
            System.out.println(Thread.currentThread().getState());
            System.out.println("Run in Thread : "+Thread.currentThread().getName());
        });

        //sebelum kita start kita bisa mengubah nama threadnya
        fajar.setName("fajar");
        System.out.println(fajar.getState());
        fajar.start();//RUNNABLE
        fajar.join();
        System.out.println(fajar.getState());//TERMINATED
    }

}
