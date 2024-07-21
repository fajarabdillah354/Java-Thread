package source.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExchangerTest {

    @Test
    void testExchanger() throws InterruptedException {
        /**
         Exchanger adalah mekanisme bertukar data antar Thread, walaupun salah satu thread sleep maka akan ditunggu, namun jika hanya 1 thread mengirimkan data, sedangkan thread lain tidak maka program akan menunggu hingga ada data yang dikirim
         */


        final var exchanger = new Exchanger<String>();//dia tipenya generic yang berarti kita akan mengirim data type String
        final var executor = Executors.newFixedThreadPool(10);

        //thread1 menerima data dari thread2 dan mengirim data First ke thread2
        executor.execute(() -> {
            try {
                System.out.println("Thread 1 Send First");
                var result = exchanger.exchange("First");
                System.out.println("Thread 1 Recieve : "+result);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        //thread2 menerima data dari thread1 dan mengirim data ke thread1
        executor.execute(() -> {
            try {
                System.out.println("Thread 2 : Send Second");
                var result = exchanger.exchange("Second");
                System.out.println("Thread 2 Recieve : "+result);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);

    }
}
