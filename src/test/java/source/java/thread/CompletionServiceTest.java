package source.java.thread;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.*;

public class CompletionServiceTest {
    private Random random = new Random();

    @Test
    void test() throws InterruptedException {
        var executor = Executors.newFixedThreadPool(10);
        var completionService = new ExecutorCompletionService<String>(executor);

        Executors.newSingleThreadExecutor().execute(() -> {
            for(int i = 0; i<100; i++){
                var index = i;
                completionService.submit(() -> {
                    Thread.sleep(random.nextInt(2000));
                   return "Task - "+index;
                });
            }
        });

        Executors.newSingleThreadExecutor().execute(() -> {
            while (true){
                try {
                    Future<String> future = completionService.poll(5,TimeUnit.SECONDS);
                    if(future == null ){
                        break;
                    }else {
                        System.out.println(future.get());
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });

        executor.awaitTermination(1,TimeUnit.DAYS);

    }
}
