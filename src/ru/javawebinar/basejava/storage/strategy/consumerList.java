package ru.javawebinar.basejava.storage.strategy;

import java.io.IOException;

public interface consumerList<T> {
    T listReturn() throws IOException;
}
