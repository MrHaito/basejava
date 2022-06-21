package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected int findResumeIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (STORAGE[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertNewResume(Resume r, int index) {
        STORAGE[size] = r;
    }

    @Override
    protected void deleteResume(int index) {
        STORAGE[index] = STORAGE[size - 1];
        STORAGE[size - 1] = null;
    }
}
