package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected Integer findSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (STORAGE[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doSaveElement(Resume r, Integer searchKey) {
        STORAGE[size] = r;
    }

    @Override
    protected void doDeleteElement(Integer searchKey) {
        STORAGE[searchKey] = STORAGE[size - 1];
    }
}