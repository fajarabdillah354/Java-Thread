package source.java.thread;

import java.awt.image.BandCombineOp;

public class Balance {

    private Long value;

    public Balance(Long value) {
        this.value = value;
    }


    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }



    //DIBAWAH INI ADALAH METHOD YANG SALAH UNTUK MENANGANI DEADLOCK
    public static void transferDeadlock(Balance from, Balance to, long value) throws InterruptedException {
        synchronized (from){//DENGAN sama saja melakukan 2 lock sekaligus sehingga proses berikutnya akan menunggu lock pertama lepas hal ini yang menyebabkan DEADLOCK condition, dimana saling tunggu antar thread
            Thread.sleep(1000L);
            synchronized (to){
                Thread.sleep(1000L);
                from.setValue(from.getValue() - value);
                to.setValue(to.getValue() + value);
            }
        }



        //Cara untuk menanganinya yaitu dengan memisah locknya(ini untuk kasus tranfer)
        synchronized (from){
            Thread.sleep(1000);
            from.setValue(from.getValue() - value);
        }

        synchronized (to){
            Thread.sleep(1000);
            to.setValue(to.getValue() + value);
        }
    }


    //DIBAWAH INI ADALAH CONTOH UNTUK MENANGANI DEADLOCK
    public static void transfer(Balance from, Balance to, long value) throws InterruptedException {


        //Cara untuk menangani Deadlock yaitu dengan memisah locknya(ini untuk kasus tranfer)
        synchronized (from){
            Thread.sleep(1000);
            from.setValue(from.getValue() - value);
        }

        synchronized (to){
            Thread.sleep(1000);
            to.setValue(to.getValue() + value);
        }
    }
}
