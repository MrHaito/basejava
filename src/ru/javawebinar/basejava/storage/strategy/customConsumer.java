package ru.javawebinar.basejava.storage.strategy;

import java.io.IOException;
import java.util.Objects;

@FunctionalInterface
public interface customConsumer<K, V> {
    void consumerWrite(K k, V v) throws IOException;
}
