package source.java.thread;

public class CounterApp {


    private Long value = 0L;

    public void increment(){
        value++;
    }

    public Long getValue() {
        return value;
    }


}
