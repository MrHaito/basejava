package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {
    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected Integer findSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "dummy");
        return Arrays.binarySearch(STORAGE, 0, size, searchKey, RESUME_COMPARATOR);
    }

    @Override
    protected void doSaveElement(Resume r, Integer searchKey) {
        int index = Math.abs((int) searchKey + 1);
        System.arraycopy(STORAGE, index, STORAGE, index + 1, size - index);
        STORAGE[index] = r;
    }

    @Override
    protected void doDeleteElement(Integer searchKey) {
        int index = searchKey;
        System.arraycopy(STORAGE, index + 1, STORAGE, index, size - 1 - index);
    }
}
