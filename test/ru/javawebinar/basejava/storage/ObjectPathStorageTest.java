package ru.javawebinar.basejava.storage;

import static org.junit.jupiter.api.Assertions.*;

class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest(Storage storage) {
        super(new ObjectPathStorage(STORAGE_DIR.toString()));
    }
}