package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void delete(String uuid) {
        int index = findResumeIndex(uuid);
        if (index >= 0) {
            System.arraycopy(STORAGE, index + 1, STORAGE, index, size - 1 - index);
            size--;
        } else {
            System.out.format("Резюме %s не найдено\n", uuid);
        }
    }

    @Override
    protected int findResumeIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(STORAGE, 0, size, searchKey);
    }

    @Override
    protected void insertNewResume(Resume r) {
        if (size == 0) {
            STORAGE[size] = r;
        } else {
            int index = Math.abs(findResumeIndex(r.getUuid()) + 1);
            System.arraycopy(STORAGE, index, STORAGE, index + 1, size - index);
            STORAGE[index] = r;
        }
        size++;
    }
}
