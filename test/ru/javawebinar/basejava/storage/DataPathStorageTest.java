package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.DataStreamSerializer;

class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new DataStreamSerializer()));
    }
}