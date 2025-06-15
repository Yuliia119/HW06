package ait.numbers.model;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorGroupSum extends GroupSum{
    public ExecutorGroupSum(int[][] numberGroups) {
        super(numberGroups);
    }

    @Override
    public int computeSum() {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Future<Integer>[] futures = new Future[numberGroups.length];
        for (int i = 0; i < numberGroups.length; i++) {
            int[] group = numberGroups[i];
            futures[i] = executor.submit(() -> Arrays.stream(group).sum());
        }
        int totalSum = 0;
        for (Future<Integer> future : futures){
            try {
                totalSum += future.get();
            } catch (InterruptedException | ExecutionException e){
                e.printStackTrace();
            }
        }
        executor.shutdown();
        return totalSum;
    }
}
