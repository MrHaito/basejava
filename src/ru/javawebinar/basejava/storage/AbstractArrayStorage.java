package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageExeption;
import ru.javawebinar.basejava.exception.NotExistStorageExeption;
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
        } else if (findResumeIndex(r.getUuid()) >= 0) {
            throw new ExistStorageExeption(r.getUuid());
        } else {
            insertNewResume(r, findResumeIndex(r.getUuid()));
            size++;
        }
    }

    @Override
    public void update(Resume r) {
        int index = findResumeIndex(r.getUuid());
        if (index >= 0) {
            System.out.format("Резюме %s найдено и обновлено\n", r.getUuid());
            STORAGE[index] = r;
        } else {
            throw new NotExistStorageExeption(r.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = findResumeIndex(uuid);
        if (index == -1) {
            throw new NotExistStorageExeption(uuid);
        }
        return STORAGE[index];
    }

    @Override
    public void delete(String uuid) {
        int index = findResumeIndex(uuid);
        if (index >= 0) {
            deleteResume(index);
            size--;
        } else {
            throw new NotExistStorageExeption(uuid);
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(STORAGE, size);
    }

    public int size() {
        return size;
    }

    protected abstract int findResumeIndex(String uuid);

    protected abstract void insertNewResume(Resume r, int index);

    protected abstract void deleteResume(int index);


}
