package com.angelo.gitapplication.feature;

import android.annotation.SuppressLint;

import java.util.UUID;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * author: Angelo.Luo
 * date : 08/29/2024 2:23 PM
 * description:
 */
public class ForkAndJoinDemo {
    public static void main(String[] args) {
        println();
        int[] arr = new int[400];
        for (int i = 0; i < 400; i++) {
            arr[i] = i + 1;
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        SumRecursiveTask sumRecursiveTask = new SumRecursiveTask(arr, 0, arr.length - 1);
        long beginTime = System.currentTimeMillis();
        Integer result = forkJoinPool.invoke(sumRecursiveTask);
        long endTime = System.currentTimeMillis();
        System.out.printf("result：%s，time spend：%s%n", result, endTime - beginTime);

        println();
        ForkJoinPool forkJoinPool2 = new ForkJoinPool();
        Fibonacci task = new Fibonacci(3);
        int result2 = forkJoinPool2.invoke(task);
        System.out.println("斐波那契数列的第10项为： " + result2);
        println();

        System.out.println(UUID.randomUUID().toString());
    }

    public static void println() {
        for (int i = 0; i < 30; i++) {
            System.out.print("\ud83c\udf47");
        }
        System.out.println();
    }
}

class SumRecursiveTask extends RecursiveTask<Integer> {

    private int[] arr;
    private int start;
    private int end;

    private static final int THRESHOLD = 100;

    public SumRecursiveTask(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            int sum = 0;
            for (int i = start; i <= end; i++) {
                sum += arr[i];
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("compute %d-%d = %d%n", start, end, sum);
            return sum;
        }
        int middle = (start + end) / 2;
        System.out.printf("split %d~%d ==> %d~%d, %d~%d%n", start, end, start, middle, middle + 1, end);
        SumRecursiveTask sumRecursiveTask1 = new SumRecursiveTask(this.arr, start, middle);
        SumRecursiveTask sumRecursiveTask2 = new SumRecursiveTask(this.arr, middle + 1, end);
        invokeAll(sumRecursiveTask1, sumRecursiveTask2);
        int result1 = sumRecursiveTask1.join();
        int result2 = sumRecursiveTask2.join();
        int result = result1 + result2;
        System.out.printf("result = %d%n", result);
        return result;
    }
}

class Fibonacci extends RecursiveTask<Integer> {
    private final int n;

    public Fibonacci(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1) {
            return n;
        }
        Fibonacci f1 = new Fibonacci(n - 1);
        System.out.printf("f1 = %d%n", n - 1);
        f1.fork();
        Fibonacci f2 = new Fibonacci(n - 2);
        System.out.printf("f2 = %d%n", n - 2);
        return f2.compute() + f1.join();
    }
}
