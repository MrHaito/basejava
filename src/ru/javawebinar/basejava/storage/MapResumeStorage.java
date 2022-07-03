package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage{
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
        return STORAGE.get(uuid);
    }

    @Override
    protected boolean isExist(Object object) {
        return object != null;
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        STORAGE.put(r.getUuid(), r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        Resume resume = (Resume) searchKey;
        STORAGE.remove(resume.getUuid());
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        STORAGE.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected List<Resume> getResumeList() {
        return new ArrayList<>(STORAGE.values());
    }
}
