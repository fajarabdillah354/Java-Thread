package source.java.thread;

public class UserLocal {

    final ThreadLocal<String> local = new ThreadLocal<>();


    public void setLocal(String value){
        local.set(value);
    }

    public void doAction(){
        var user = local.get();
        System.out.println(user+" do action");
    }


}
