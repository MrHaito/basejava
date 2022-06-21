package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] STORAGE = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(STORAGE, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Место закончилось", r.getUuid());
        }
        isExist(r);
        insertNewResume(r, getSearchKey(r.getUuid()));
        size++;
    }

    @Override
    public void delete(String uuid) {
        notExist(uuid);
        deleteResume(getSearchKey(uuid));
        size--;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(STORAGE, size);
    }

    public int size() {
        return size;
    }

    @Override
    protected void updateResume(int index, Resume r) {
        STORAGE[index] = r;
    }

    @Override
    protected Resume getResumeByIndex(int index) {
        return STORAGE[index];
    }
}
