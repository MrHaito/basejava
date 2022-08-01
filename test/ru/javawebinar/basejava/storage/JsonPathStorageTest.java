package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {

    protected JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new JsonStreamSerializer()));
    }
}
