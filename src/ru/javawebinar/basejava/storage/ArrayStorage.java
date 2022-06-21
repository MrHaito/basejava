package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
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
    protected void doSave(Resume r, Object searchKey) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Место закончилось", r.getUuid());
        }
        STORAGE[size] = r;
        size++;
    }

    @Override
    protected void doDelete(Object searchKey) {
        STORAGE[(int) searchKey] = STORAGE[size - 1];
        STORAGE[size - 1] = null;
        size--;
    }
}
