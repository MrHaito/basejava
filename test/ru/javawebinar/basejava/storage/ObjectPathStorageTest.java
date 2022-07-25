package ru.javawebinar.basejava.storage;

class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new ObjectPathStorage(STORAGE_DIR));
    }
}