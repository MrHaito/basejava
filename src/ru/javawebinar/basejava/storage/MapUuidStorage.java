package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {
    protected final Map<String, Resume> STORAGE = new HashMap<>();

    @Override
    public void clear() {
        STORAGE.clear();
    }

    @Override
    public int size() {
        return STORAGE.size();
    }

    @Override
    protected String findSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String object) {
        if (object == null) {
            return false;
        }
        return STORAGE.containsKey(object);
    }

    @Override
    protected void doSave(Resume r, String searchKey) {
        STORAGE.put(searchKey, r);
    }

    @Override
    protected void doDelete(String searchKey) {
        STORAGE.remove(searchKey);
    }

    @Override
    protected void doUpdate(Resume r, String searchKey) {
        STORAGE.put(searchKey, r);
    }

    @Override
    protected Resume doGet(String searchKey) {
        return STORAGE.get(searchKey);
    }

    @Override
    protected List<Resume> getResumeList() {
        return new ArrayList<>(STORAGE.values());
    }
}
