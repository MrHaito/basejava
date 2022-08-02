package ru.javawebinar.basejava.storage.strategy;

import java.io.IOException;

public interface consumerWriter<K> {
    void write(K k) throws IOException;
}
