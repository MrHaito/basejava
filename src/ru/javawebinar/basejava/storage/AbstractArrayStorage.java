package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] STORAGE = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(STORAGE, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    @Override
    protected void doSave(Resume r, Integer searchKey) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Место закончилось", r.getUuid());
        }
        doSaveElement(r, searchKey);
        size++;
    }

    @Override
    protected void doDelete(Integer searchKey) {
        doDeleteElement(searchKey);
        STORAGE[size - 1] = null;
        size--;
    }

    @Override
    protected void doUpdate(Resume r, Integer searchKey) {
        STORAGE[searchKey] = r;
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return STORAGE[searchKey];
    }

    @Override
    protected boolean isExist(Integer object) {
        return object >= 0;
    }

    protected abstract void doSaveElement(Resume r, Integer searchKey);

    protected abstract void doDeleteElement(Integer searchKey);

    @Override
    protected List<Resume> getResumeList() {
        return Arrays.asList(Arrays.copyOf(STORAGE, size));
    }
}
