package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage{
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
        STORAGE.remove((String) searchKey);
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        STORAGE.put((String) searchKey, r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return STORAGE.get((String) searchKey);
    }

    @Override
    protected List<Resume> getResumeList() {
        List<Resume> resumes = new ArrayList<>(STORAGE.values());
        return resumes;
    }
}
