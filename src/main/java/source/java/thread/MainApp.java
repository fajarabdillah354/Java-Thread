package source.java.thread;

public class MainApp {
    public static void main(String[] args) {
        //ini adalah thread yang dibuat otomatis meskipun kita tidak mmembuat Thread tapi running dalam Thread
        String name = Thread.currentThread().getName();

        System.out.println(name);
    }


}
