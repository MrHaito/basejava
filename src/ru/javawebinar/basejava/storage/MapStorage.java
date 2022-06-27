package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage{
    protected final HashMap<String, Resume> STORAGE = new HashMap<>();
    @Override
    public void clear() {
        STORAGE.clear();
    }

    @Override
    public Resume[] getAll() {
        return STORAGE.values().toArray(new Resume[STORAGE.size()]);
    }

    @Override
    public int size() {
        return STORAGE.size();
    }

    @Override
    protected Object findSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object object) {
        if (object == null) {
            return false;
        }
        return STORAGE.containsKey((String) object);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        STORAGE.put((String) searchKey, r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        STORAGE.remove(searchKey);
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        STORAGE.put((String) searchKey, r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return STORAGE.get((String) searchKey);
    }
}
