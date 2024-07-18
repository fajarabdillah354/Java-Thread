package source.java.thread;

import java.util.concurrent.atomic.AtomicLong;

public class CounterAtomic {

    private AtomicLong value = new AtomicLong(0L);//untuk menggunakan atomic kita butuh class AtomicLong(ini contoh masih banyak lagi silahkan lihat di docnnya)

    public void increment(){
        value.getAndIncrement();//untuk melakukann increment kita tinggal panggi method getAndIncrement()
    }

    public Long get(){
        return value.get();//lalu untuk mendapatkan valuenya tinggal panggil method get()
    }


}
