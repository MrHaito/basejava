package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected Object findSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (STORAGE[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doSaveElement(Resume r, Object searchKey) {
        STORAGE[size] = r;
    }

    @Override
    protected void doDeleteElemet(Object searchKey) {
        STORAGE[(int) searchKey] = STORAGE[size - 1];
    }
}