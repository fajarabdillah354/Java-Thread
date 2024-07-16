package source.java.thread;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

    @Test
    void testTimer() throws InterruptedException {


        var task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Delayed job");
            }
        };

        var timer = new Timer();
        timer.schedule(task, 3000);//setelah delay 3 detik program akan berjalan


        Thread.sleep(4000);//ini untuk mengecek karna jika memakai Junit langsung keluar dari program, makannya dibuat sleep dulu selema 4 detik baru diliat tasknya berjalan atau tidak

    }


    @Test
    void testPeriodic() throws InterruptedException {
        var task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Periodic job");
            }
        };

        /*
        Pertama program akan delay selama 2detik lalu setelah itu setiap 2detik program akan berjalan, ini akan terus berlangsung selama aplikasi berjalan
         */
        var timer = new Timer();
        timer.schedule(task, 2000,2000);

        Thread.sleep(10_000);//untuk melihat hasil periodicnya


    }
}
