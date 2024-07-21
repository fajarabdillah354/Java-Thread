package source.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ScheduleExecutorTest {

    @Test
    void testDelayedJob() throws InterruptedException {
        var executor = Executors.newScheduledThreadPool(10);//untuk melakukan schedule kita bisa menggunakan method newScheduleThreadPool() sebagai Executors dimana tempat thread di tugaskan


        var future = executor.schedule(() -> System.out.println("delayed job"), 5, TimeUnit.SECONDS);//untuk menggunakan kita menggunakan method schedule()

        System.out.println(future.getDelay(TimeUnit.SECONDS));

        executor.awaitTermination(1,TimeUnit.DAYS);//untuk memastikan apakah delayedd job bekerja kita menunggu thread sampai 1 hari


    }


    @Test
    void testPeriodicJob() throws InterruptedException {
        var executor = Executors.newScheduledThreadPool(10);


        var future = executor.scheduleAtFixedRate(() -> System.out.println("delayed job"), 3,3, TimeUnit.SECONDS);

        System.out.println(future.getDelay(TimeUnit.SECONDS));


        executor.awaitTermination(1,TimeUnit.DAYS);


    }



}
