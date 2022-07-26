package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.ObjectStreamSerializer;

class ObjectStreamSerializerTest extends AbstractStorageTest {

    public ObjectStreamSerializerTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}