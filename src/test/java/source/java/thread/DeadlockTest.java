package source.java.thread;

import org.junit.jupiter.api.Test;

public class DeadlockTest {


    /*
    Deadlock merupakan kondisi dimana beberapa thread saling menunggu satu sama lain karena biasanya terjadi ketika thread tersebut melakukan lock dan menunggu lock lain dari thread lain dan ternyata thread tersebut juga menunggu lock lain
     */
    @Test
    void testDeadlock() throws InterruptedException {

        var balance1 = new Balance(1000000L);
        var balance2 = new Balance(1000000L);

        var thread1 = new Thread(() -> {//Thread 1 transfet ke Thread 2 sebanyak 500k
            try {
                Balance.transfer(balance1,balance2,500000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        var thread2 = new Thread(() -> {//Thread 2 tranfer ke Thread 1 sebanyak 500k(uang Thread 1 harusnya sama saja tidak berkurang)
            try {
                Balance.transfer(balance2,balance1,500000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Balance 1 : "+balance1.getValue());
        System.out.println("Balance 2 : "+balance2.getValue());

    }
}
