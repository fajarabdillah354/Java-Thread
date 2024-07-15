package source.java.thread;

public class SynchronizedCounter {



        /*
         * dengan menggunakan kata kunci syncronized pada method yang mengandung race condition, kita bisa memaksa setiap thread yang masuk ke dalamnya 1 per 1 yaitu menunggu proses yang sedang berlangsung selesai dulu. dengan begitu tidak ada data yang terlewatkan
         * Synchronization di Java sebenarnya bekerja menggunakan lock
         * Ketika kita melakukan synchronized method, secara otomatis Java akan membuat intrinsic lock atau monitor lock
         * Ketika synchronized method dipanggil oleh thread, thread akan melakukan mencoba mendapatkan intrinsic lock, setelah method selesai (sukses ataupun error), maka thread akan mengembalikan intrinsic lock
         * Semua itu terjadi secara otomatis di synchronized method
         */
        private Long value = 0L;

        /*
        untuk membuat synchronized ada 2 cara yaitu synchronized method dan syschronized statement
         */
        public synchronized void increment() {//ini contoh dari synchronized method

            /*
            contoh synchronized statement

            synchronized (objek yang mau disynchorized){
                //isi objectnya

            }
             */

            //isi field lain yang tidak di lock


            synchronized (value){//ini contoh dari synchronized statement,isi parameter adalah object yang bisa menyebabkan race condition
                value++;
            }

            //isi field lain yang tidak di lock

        }

        public Long getValue() {
            return value;
        }


}
