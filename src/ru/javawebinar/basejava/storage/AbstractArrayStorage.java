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

    public Resume[] getAll() {
        return Arrays.copyOf(STORAGE, size);
    }

    public int size() {
        return size;
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Место закончилось", r.getUuid());
        }
        doSaveElement(r, searchKey);
        size++;
    }

    @Override
    protected void doDelete(Object searchKey) {
        doDeleteElement(searchKey);
        STORAGE[size - 1] = null;
        size--;
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        STORAGE[(int) searchKey] = r;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return STORAGE[(int) searchKey];
    }

    @Override
    protected boolean isExist(Object object) {
        return (int) object >= 0;
    }

    protected abstract void doSaveElement(Resume r, Object searchKey);

    protected abstract void doDeleteElement(Object searchKey);
}
