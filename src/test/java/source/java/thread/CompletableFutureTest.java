package source.java.thread;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.util.Random;
import java.util.SplittableRandom;
import java.util.concurrent.*;

public class CompletableFutureTest {

    /**
     * Pada Java 8, terdapat sebuah class baru bernama CompletableFuture, ini merupakan implementasi Future yang bisa kita set datanya secara manual
     * CompletableFuture sangat cocok ketika kita misal perlu membuat future secara manual, sehingga kita tidak memerlukan Callable
     * Untuk memberi value terhadap CompletableFuture secara manual, kita bisa menggunakan method complete(value) atau completeExceptionally(error) untuk error
     */



    private ExecutorService executor = Executors.newFixedThreadPool(10);
    private Random random = new Random();

    CompletableFuture<String> getValue() throws InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();
        //jika disini tidak ada nilainya sementara pada Future kita memanggil method get(), maka program akan bengong tidak ngapa2in, karna belum ada proses yang dimasukkan

        executor.execute(() -> {

            try {
                Thread.sleep(2000);
                future.complete("fajar abdillah ahmad");
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }
        });
        return future;
    }


    @Test
    void testCompletableFuture() throws ExecutionException, InterruptedException {
        Future<String> stringFuture = getValue();
        System.out.println(stringFuture.get());

    }


    //CompletableFuture (2)
    public void execute(CompletableFuture<String> future, String value){
        executor.execute(() -> {
            try {
                Thread.sleep(1000+random.nextInt(5000));
                future.complete(value);
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }

        });
    }

    public Future<String> getFastest(){
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        execute(completableFuture, "Thread 1");
        execute(completableFuture, "Thread 2");
        execute(completableFuture, "Thread 3");
        return completableFuture;
    }

    @Test
    void testGetFastest() throws ExecutionException, InterruptedException {
        System.out.println(getFastest().get());
    }


    @Test
    void testComplationStage() throws InterruptedException, ExecutionException {
        /**
         * CompletableFuture merupakan turunan dari interface CompletionStage
         * CompletionStage merupakan fitur dimana kita bisa menambahkan asynchronous computation, tanpa harus menunggu dulu data dari Future nya ada
         * CompletionStage sangat mirip dengan operation di Java Stream, hanya saja tidak sekomplit di Java Stream
         */



        CompletableFuture<String> future1 = getValue();

        CompletableFuture<String[]> future2 = future1
                .thenApply(string -> string.toUpperCase())
                .thenApply(string -> string.split(" "))
                .thenApply(strings -> strings.clone());

        String[] strings = future2.get();
        for (var getStrings : strings){
            System.out.println(getStrings);
        }

    }
}
