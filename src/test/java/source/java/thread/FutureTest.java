package source.java.thread;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FutureTest {


    /**
      Callable<T>
     * Sebelumnya kita selalu menggunakan Runnable untuk mengirim perintah ke thread, namun pada Runnable, setelah pekerjaan selesai, tidak ada data yang dikembalikan sama sekali, karena method nya return void
     * Callable mirip dengan Runnable, namun Callable mengembalikan data
     * Selain itu Callable merupakan generic type, sehingga kita bisa tentukan tipe return data nya


      Future<T>
     *  Jika kita ingin mengeksekusi callable, kita bisa menggunakan method submit(Callable) pada ExecutorService, method submit(Callable) tersebut akan mengembalikan Future<T>
     * Future merupakan representasi data yang akan dikembalikan oleh proses asynchronous
     * Menggunakan Future, kita bisa mengecek apakah pekerjaan Callable sudah selesai atau belum, dan juga mendapat data hasil dari Callable

        Future Method
     1. T get()     -> Mengambil result data, jika belum ada, maka akan menunggu sampai ada
     2. T get(timeout, time unit)    -> Mengambil result data, jika belum ada, maka akan menunggu sampai timeout
     3. void cancel(mayInterrupt)   -> Membatalkan proses callable, dan apakah diperbolehkan di interrupt jika sudah terlanjur berjalan
     4. boolean isCancelled()   -> Mengecek apakah future dibatalkan
     5. boolean isDone()   -> Mengecek apakah future telah selesai
     * @throws ExecutionException
     * @throws InterruptedException
     */

    @Test
    void testFuture() throws ExecutionException, InterruptedException {
        var executor = Executors.newSingleThreadExecutor();
        Callable<String> callable = () -> {
            Thread.sleep(5000);//jika menggunakan Callable kita tidak perlu menambahkan throw exeption ketika Thread dibuat
            return "hi";
        };

        //kita bisa memasukkan Callable ke dalam parameter method submit dengan langsung memasukan lambda function
        Future<String> future = executor.submit(callable);
        System.out.println("Future selesai dibuat, menunggu task masuk");

        //akan mengecek dan menunggu hingga task diberikan, pada Calable diatas kita memberikan sleep kepada task selama 5 detik jadi disini akan ditunggu selama 5 detik hingga task masuk
        while (!future.isDone()){
            Thread.sleep(1000);
            System.out.println("Menunggu task masuk dan Future!");//ketika dijalankan akan langsung keluar dengan durasi keluarnya per 1 detik
        }


        String getResult = future.get();//kita juga bisa mendapatkan isi dari futurenya dengan method get() namun akan throw execeptiopn
        System.out.println(getResult);

    }



    @Test
    void testFutureCancelled() throws ExecutionException, InterruptedException {
        var executor = Executors.newSingleThreadExecutor();
        Callable<String> callable = () -> {
            Thread.sleep(5000);//jika menggunakan Callable kita tidak perlu menambahkan throw exeption ketika Thread dibuat
            return "hi";
        };

        //kita bisa memasukkan Callable ke dalam parameter method submit dengan langsung memasukan lambda function
        Future<String> future = executor.submit(callable);
        System.out.println("Future selesai dibuat, menunggu task masuk");
        Thread.sleep(3000);//akan menunggu selama 3 detik lalu setelah karna Thread yang ada di Callable diatas sleep selama 5 detik sementara di Future slepp selama 3 detik maka tugas yang ada di Callable akan di interupt
        future.cancel(true);

        System.out.println("future telah di cancelled : "+future.isDone());
        String getResult = future.get();//kita juga bisa mendapatkan isi dari futurenya dengan method get() namun akan throw execeptiopn
        System.out.println(getResult);
    }


    @Test
    void testFutureInovokeAll() throws ExecutionException, InterruptedException {
        var executor = Executors.newFixedThreadPool(10);//10 thread yang dimasukan ke tugas

        //membuat Interface List untuk menampung variabel callables, lalu di beri perulangan dengan IntStream dimana rangenya dari 1 - 10 lalu dikonversi ke object, setelah itu lambda function yang kita konversi ke dalam Callable<String>
        List<Callable<String>> callables = IntStream.range(1,11).mapToObj(value -> (Callable<String>) () -> {
            //kita membuat sleep dari 10 thread yang dimasukan dimana setiap perulangan dari value akan di * dengan 0,5 milisecond
            Thread.sleep(value*500L);
            return String.valueOf(value);//mengembalikan nilai valur namun di konversi ke dalam string
        }).collect(Collectors.toList());//kita konversi ke collection dengan hasil List


        var future = executor.invokeAll(callables);//penggunaan method invokeAll() untuk meng eksekuka banyak thread callables sekaligus (dengan syarat menunggu semua thread selesai baru di get)

        for (var getFuture : future){
            System.out.println(getFuture.get());//menampilkan isi dari future dengan perulangan
        }

    }



    @Test
    void testFutureInovokeAny() throws ExecutionException, InterruptedException {

        /*
        - Kadang ada kasus dimana kita ingin mengeksekusi beberapa proses secara asynchronous, namun ingin mendapatkan hasil yang paling cepat
        - Hal ini bisa dilakukan dengan menggunakan method invokeAny(Collection<Callable<T>>)
        - invokeAny() akan mengembalikan result data dari Callable yang paling cepat mengembalikan result

         */


        var executor = Executors.newFixedThreadPool(10);//10 thread yang dimasukan ke tugas

        //membuat Interface List untuk menampung variabel callables, lalu di beri perulangan dengan IntStream dimana rangenya dari 1 - 10 lalu dikonversi ke object, setelah itu lambda function yang kita konversi ke dalam Callable<String>
        List<Callable<String>> callables = IntStream.range(1, 11).mapToObj(value -> (Callable<String>) () -> {

            Thread.sleep(value * 500L);
            return String.valueOf(value);
        }).collect(Collectors.toList());


        var future = executor.invokeAny(callables);//invokeAny() digunakan untuk mengambil beberapa proses secara asynchronus namun yang hanya yang paling cepat saja
        System.out.println(future);


    }


}
