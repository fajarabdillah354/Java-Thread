package source.java.thread;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentMap {

    /** Concurrent Map
     * ConcurrentMap merupakan turunan dari Map yang thread safe, dan cocok jika memang diakses oleh lebih dari satu thread
     * Tidak ada hal yang spesial dari ConcurrentMap, semua operasi method nya sama seperti Map, yang membedakan adalah pada ConcurrentMap, dijamin thread safe
     * Implementasi dari interface ConcurrentMap adalah class ConcurrentHashMap
     * @throws InterruptedException
     */
    @Test
    void testConcurrent() throws InterruptedException {
        final var countDown = new CountDownLatch(50);
        final var map = new ConcurrentHashMap<Integer, String>();
        final var executor = Executors.newFixedThreadPool(50);

        for (int i = 0; i < 50; i++) {
            final var index = i;
            executor.execute(() -> {
                try {
                    Thread.sleep(1000);
                    map.putIfAbsent(index, " Data ke "+index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDown.countDown();
                }

            });
        }

        executor.execute(() -> {
            try {
                countDown.await();
                map.forEach((integer, s) -> System.out.println(integer+ " : "+s));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }



    @Test
    void testCollection(){
        //jika menggunakan collection data kita kurang aman terhadap multithread pada kasus ini kita pastinya ingin merubah data kita menjadi multi thread, pada Collection kita bisa menggunakan method synchorinized sebagai helper

        List<String> list = List.of("fajar","abdillah","ahmad");
        Collections.synchronizedList(list);

    }
}
