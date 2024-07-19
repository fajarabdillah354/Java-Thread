package source.java.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CounterLock {

    public Long value = 0L;
    final Lock lock = new ReentrantLock();//kita bisa mengganti penggunaaan synchorinze dengan Lock

    public void increment(){//Dengan cara kita menggunakan method lock()
        try{
            lock.lock();
            value++;
        }finally {
            lock.unlock();//lalu setelah selesai kita unlock()
        }
    }


    public Long getValue() {
        return value;
    }


}
