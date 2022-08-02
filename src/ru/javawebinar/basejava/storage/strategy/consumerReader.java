package ru.javawebinar.basejava.storage.strategy;

import java.io.IOException;

@FunctionalInterface
public interface consumerReader<K> {
    void read() throws IOException;
}
