package source.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class ReactiveStreamTest {


    @Test
    void testPubsliher() throws InterruptedException {

        var executor = Executors.newFixedThreadPool(10);
        var publisher = new SubmissionPublisher<String>();

        PrintSubscribe subscribe1 = new PrintSubscribe(" Data A", 4000L);
        PrintSubscribe subscribe2 = new PrintSubscribe(" Data B", 500L);//ini untuk menguji independennya maka dibuat 2 object, disini tidak peduli kalo subsciber1 lambat dan subsciber2 cepat
        publisher.subscribe(subscribe1);
        publisher.subscribe(subscribe2);

        executor.execute(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(3000);
                    publisher.submit("fajar ke- "+i);
                    System.out.println("Send fajar - "+i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);

        Thread.sleep(100*1000);

    }


    @Test
    void testBufferPubsliher() throws InterruptedException {

        /**
         * Buffer adalah tempat menaruh data sementara, default ukuran buffer yaitu 256 artinya jika kita memberi task terlalu banyak maka pada task ke 256 data akan berhenti dan menunggu di ambil oleh subscriber terlebih dahulu
         */

        var executor = Executors.newFixedThreadPool(10);
        var publisher = new SubmissionPublisher<String>(Executors.newWorkStealingPool(), 10);// untuk melakukan Buffer kita perlu menambahkan parameter pada class SubmissionPublishernya

        PrintSubscribe subscribe1 = new PrintSubscribe(" Data A", 1000L);
        PrintSubscribe subscribe2 = new PrintSubscribe(" Data B", 500L);//ini untuk menguji independennya maka dibuat 2 object, disini tidak peduli kalo subsciber1 lambat dan subsciber2 cepat
        publisher.subscribe(subscribe1);
        publisher.subscribe(subscribe2);

        executor.execute(() -> {
            for (int i = 0; i < 1000; i++) {
                publisher.submit("fajar ke- "+i);
                System.out.println("Send fajar - "+i);
            }
        });
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);

        Thread.sleep(100*1000);

    }

    public static class PrintSubscribe implements Flow.Subscriber{
        /**
         * kalo publishernya lambar tidak masalah karna subscribernya akan menunggu ini karena keduanya independen
         * ini enaknya mengirim data memakai reactive stream dimana kita mau mengirim data dan tidak peduli dengan kecepatan penerimanya
         * jika yang lambat publishernya maka proses pengambilan data (subcriber)akan menunggu hingga data publisher dikirim
         */

        private Flow.Subscription subscriber;

        private String name;

        private Long valueLong;

        public PrintSubscribe(String name, Long valueLong) {
            this.name = name;
            this.valueLong = valueLong;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscriber = subscription;
            this.subscriber.request(1);
        }

        @Override
        public void onNext(Object item) {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + name +" : " + item);
                this.subscriber.request(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onError(Throwable throwable) {
            throwable.printStackTrace();
        }

        @Override
        public void onComplete() {
            System.out.println(Thread.currentThread().getName() + " Done ");
        }
    }


    @Test
    void testProcessor() throws InterruptedException {
        /**
         * Dengan menggunakan Processor kita bisa menjadi Publisher dan juga bisa menjadi Subscriber, ini sama aja Processor sebagai tempat mengolah data yang masuk dari Publisher dimana data yang diolah bisa dikirim dan akan diterima oleh subscriber
         */

        var executor = Executors.newFixedThreadPool(10);
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        var processor = new HelloProcessor();
        publisher.subscribe(processor);

        var subscriber = new PrintSubscribe(" A_A ", 1000L);
        processor.subscribe(subscriber);

        executor.execute(() -> {
            for (int i = 0; i < 10; i++) {
                    publisher.submit(" fajar ke- "+i);
                    System.out.println(Thread.currentThread().getName() + " Send fajar - "+i);
            }
        });

        Thread.sleep(100*1000);




    }

    public class HelloProcessor extends SubmissionPublisher<String> implements Flow.Processor<String, String>{

        private Flow.Subscription subscription;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            this.subscription.request(1);
        }

        @Override
        public void onNext(String item) {
            var value = "Hello "+ item;
            submit(value);//kita juga bisa menjadi publisher dengan method submit()
            this.subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {
            throwable.printStackTrace();
        }

        @Override
        public void onComplete() {
            close();
        }
    }


}
