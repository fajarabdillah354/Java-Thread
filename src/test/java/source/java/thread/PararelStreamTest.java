package source.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PararelStreamTest {

    /** Parallel Stream
     * Salah satu fitur yang menarik di Java Stream adalah, Stream bisa dijalankan secara parallel
     * secara default pararel akan dijalankan di ForkJoinPool , dimana akan dirunning secara default menggunakan sejumlah thread yang ada di cpu kita
     * Agar stream bisa berjalan parallel, kita cukup gunakan method parallel()
     */

    @Test
    void testStreamPararel() {

        Stream<Integer> stream = IntStream.range(1,500).boxed();
        stream.parallel().forEach(integer -> System.out.println(Thread.currentThread().getName() + " : " + integer));
        System.out.println(stream);

//        stream.forEach(integer -> System.out.println(Thread.currentThread().getName() + " : " + integer)); // adalah contoh tidak menggunakan Thread atau pararell

    }


    @Test
    void testCostomStreamPararell() {

        /** Custom ForkJoinPool
         * Method parallel() di Java Stream tidak memiliki parameter ForkJoinPool sama sekali, lantas bagaimana jika kita ingin menggunakan ForkJoinPool yang kita buat?
         * Kita bisa mengeksekusi parallel stream nya dalam task nya ForkJoinPool
         */

        ForkJoinPool pool = new ForkJoinPool(1);

        ForkJoinTask<?> task = pool.submit(() -> {
            Stream<Integer> stream = IntStream.range(1,500).boxed();
            stream.parallel().forEach(integer -> {
                System.out.println(Thread.currentThread().getName()+" : "+integer);
            });
        });

        task.join();
    }
}
