package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageExeption;
import ru.javawebinar.basejava.exception.NotExistStorageExeption;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume r) {
        Object searchKey = findExistingSearchKey(r.getUuid());
        doSave(r, searchKey);
    }

    @Override
    public void update(Resume r) {
        Object searchKey = findNotExistingSearchKey(r.getUuid());
        if (isExist(searchKey)) {
            System.out.format("Резюме %s найдено и обновлено\n", r.getUuid());
            doUpdate(r, searchKey);
        }
    }

    @Override
    public Resume get(String uuid) {
        return doGet(findNotExistingSearchKey(uuid));
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = findNotExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    protected abstract Object findSearchKey(String uuid);

    protected Object findExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageExeption(uuid);
        }
        return searchKey;
    }

    protected Object findNotExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageExeption(uuid);
        }
        return searchKey;
    }

    protected abstract boolean isExist(Object object);

    protected abstract void doSave(Resume r, Object searchKey);

    protected abstract void doDelete(Object searchKey);

    protected abstract void doUpdate(Resume r, Object searchKey);

    protected abstract Resume doGet(Object searchKey);
}
