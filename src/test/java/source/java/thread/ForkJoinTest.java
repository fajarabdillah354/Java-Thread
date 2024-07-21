package source.java.thread;

import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ForkJoinTest {

    //create forkjoin

    @Test
    void testCreate() {
        var pool1 = new ForkJoinPool();// sebanyak total core device
        var pool2 = new ForkJoinPool(5);//membatasi 5



        ExecutorService service1 = Executors.newWorkStealingPool();
        ExecutorService service2 = Executors.newWorkStealingPool(5);

    }


    @Test
    void testRecursiveAction() throws InterruptedException {
        var pool = new ForkJoinPool();//akan memecah sebanyak thread yang mampu dikerjakan device
        List<Integer> integers = IntStream.range(1,500).boxed().collect(Collectors.toList());


        pool.execute(new SimpleForkJoin(integers));
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.DAYS);

    }

    public static class SimpleForkJoin extends RecursiveAction{

        private List<Integer> integers;

        public SimpleForkJoin(List<Integer> integers) {
            this.integers = integers;
        }

        @Override
        protected void compute() {
            if (integers.size() <= 10){
                doFork();
            }else{
                forkCompute();
            }
        }

        private void forkCompute() {
            List<Integer> list = this.integers.subList(0, integers.size()/2);
            SimpleForkJoin task1 = new SimpleForkJoin(list);

            List<Integer> list2 = this.integers.subList(integers.size()/2 , this.integers.size());
            SimpleForkJoin task2 = new SimpleForkJoin(list2);

            ForkJoinTask.invokeAll(task1,task2);

        }

        private void doFork() {
            integers.forEach(integer -> System.out.println(Thread.currentThread().getName() + " : " +integer));
        }

    }


    @Test
    void testTotalForkJoin() {

        /**
         * ForkJoinTask biasanya digunakan ketika kita ingin mengupload data yang besar contoh upload data ratusan ke DB, kita bisa split pertask 100 dari 100 ribu ini akan lebih cepat
         */

        var pool = ForkJoinPool.commonPool();;
        List<Integer> integers = IntStream.range(1,500).boxed().collect(Collectors.toList());
        TotalForkJoin totalForkJoin = new TotalForkJoin(integers);

        pool.submit(totalForkJoin);
        Long total = totalForkJoin.join();
        System.out.println(total);


        long sum = integers.stream().mapToLong(value -> value).sum();
        System.out.println(sum);

    }

    public static class TotalForkJoin extends RecursiveTask<Long>{

        private List<Integer> integers;

        public TotalForkJoin(List<Integer> integers) {
            this.integers = integers;
        }

        @Override
        protected Long compute() {
            if (integers.size() <= 10){
                return doLongFork();
            }else {
                return computoLong();
            }
        }

        private Long computoLong() {
            List<Integer> task1 = this.integers.subList(0, integers.size()/2);
            List<Integer> task2 = this.integers.subList(integers.size()/2 , integers.size());

            TotalForkJoin totalForkJoin1 = new TotalForkJoin(task1);
            TotalForkJoin totalForkJoin2 = new TotalForkJoin(task2);

            ForkJoinTask.invokeAll(totalForkJoin1,totalForkJoin2);
            return totalForkJoin1.join() + totalForkJoin2.join();

        }

        private Long doLongFork() {
            return integers.stream().mapToLong(value -> value).peek(value -> System.out.println(Thread.currentThread().getName()+" : "+ value)).sum();
        }
    }



}
