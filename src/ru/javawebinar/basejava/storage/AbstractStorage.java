package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageExeption;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected void isExist(Resume r) {
        if (getSearchKey(r.getUuid()) >= 0) {
            throw new ExistStorageExeption(r.getUuid());
        }
    }

    protected abstract int getSearchKey(String uuid);

    protected abstract void insertNewResume(Resume r, int index);

    protected abstract void deleteResume(int index);
}
