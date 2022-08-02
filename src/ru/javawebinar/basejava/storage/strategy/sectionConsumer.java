package ru.javawebinar.basejava.storage.strategy;

import java.io.IOException;

public interface sectionConsumer<K> {
    void write(K k) throws IOException;
}
