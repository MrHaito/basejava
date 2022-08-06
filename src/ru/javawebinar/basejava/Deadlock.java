package ru.javawebinar.basejava;

public class Deadlock {
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> extracted(LOCK1, LOCK2));
        Thread t2 = new Thread(() -> extracted(LOCK2, LOCK1));

        t1.start();
        t2.start();
    }

    private static void extracted(Object lock1, Object lock2) {
        synchronized (lock1) {
            System.out.println("Thread 1: locked LOCK1");
            synchronized (lock2) {
                System.out.println("Thread 1: locked LOCK2");
            }
        }
    }
}
