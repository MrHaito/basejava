package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected Object findSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(STORAGE, 0, size, searchKey);
    }

    @Override
    protected void doSaveElement(Resume r, Object searchKey) {
        int index = Math.abs((int) searchKey + 1);
        System.arraycopy(STORAGE, index, STORAGE, index + 1, size - index);
        STORAGE[index] = r;
    }

    @Override
    protected void doDeleteElement(Object searchKey) {
        int index = (int) searchKey;
        System.arraycopy(STORAGE, index + 1, STORAGE, index, size - 1 - index);
    }
}
