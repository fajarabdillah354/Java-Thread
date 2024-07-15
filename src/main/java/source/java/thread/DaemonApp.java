package source.java.thread;

public class DaemonApp {
//DAEMONTHREAD

    /*
    secara default di java(selain Junit) ketika Thread berjalan akan ditunggu sampai selesai yang disebut user Thread.
    - kita bisa mengubah thread menjadi daemon thread, menggunakan setDaemon(true), maka secara otomatis thread akan menjadi daemon thread
    - daemon thread tidak ditunggu beda dengan user thread, dia akan langsung menghentikan program dengan system.exit(). maka thread pun akan otomatis terhenti

     */
    public static void main(String[] args) {
        var fajar = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Run Thread");//setelah menunggu 3 detik akan tampil output ini, namun jika menggunakan daemon akan langsung di system.exit()
        });

        fajar.setDaemon(true);//nilai parameter harus kita masukan
        fajar.start();

    }

}
