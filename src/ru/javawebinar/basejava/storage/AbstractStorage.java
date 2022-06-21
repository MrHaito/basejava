package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageExeption;
import ru.javawebinar.basejava.exception.NotExistStorageExeption;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume r) {
        int index = getSearchKey(r.getUuid());
        if (index >= 0) {
            System.out.format("Резюме %s найдено и обновлено\n", r.getUuid());
            updateResume(index, r);
        } else {
            throw new NotExistStorageExeption(r.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        notExist(uuid);
        return getResumeByIndex(getSearchKey(uuid));
    }

    protected void isExist(Resume r) {
        if (getSearchKey(r.getUuid()) >= 0) {
            throw new ExistStorageExeption(r.getUuid());
        }
    }

    protected void notExist(String uuid) {
        int index = getSearchKey(uuid);
        if (index == -1) {
            throw new NotExistStorageExeption(uuid);
        }
    }

    protected abstract int getSearchKey(String uuid);

    protected abstract void insertNewResume(Resume r, int index);

    protected abstract void deleteResume(int index);

    protected abstract void updateResume(int index, Resume r);

    protected abstract Resume getResumeByIndex(int index);
}
