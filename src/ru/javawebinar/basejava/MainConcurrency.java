package ru.javawebinar.basejava;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private static volatile int counter;
    private static final AtomicInteger atomicInteger = new AtomicInteger();

    private static final ThreadLocal<DateTimeFormatter> threadLocal =
            ThreadLocal.withInitial(() -> DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));

    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    private static final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static final Lock WRITE_LOCK = reentrantReadWriteLock.writeLock();
    private static final Lock READ_LOCK = reentrantReadWriteLock.readLock();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        thread0.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
                throw new IllegalStateException();
            }
        }).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService = Executors.newCachedThreadPool();
//        CompletionService completionService = new ExecutorCompletionService(executorService);
//        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() -> {
//            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                    System.out.println(threadLocal.get());
                }
                latch.countDown();
                return counter;
            });
//            thread.start();
//            threads.add(thread);
        }

//        threads.forEach(t -> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });

        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
//        System.out.println(counter);
        System.out.println(atomicInteger.get());

        LazySingleton.getInstance();
    }

    private void inc() {
//        lock.lock();
//        try {
//        counter++;
//        } finally {
//            lock.unlock();
//        }
        atomicInteger.incrementAndGet();
    }
}
