package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
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
    protected Resume findSearchKey(String uuid) {
        return STORAGE.get(uuid);
    }

    @Override
    protected boolean isExist(Resume object) {
        return object != null;
    }

    @Override
    protected void doSave(Resume r, Resume searchKey) {
        STORAGE.put(r.getUuid(), r);
    }

    @Override
    protected void doDelete(Resume searchKey) {
        Resume resume = searchKey;
        STORAGE.remove(resume.getUuid());
    }

    @Override
    protected void doUpdate(Resume r, Resume searchKey) {
        STORAGE.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected List<Resume> getResumeList() {
        return new ArrayList<>(STORAGE.values());
    }
}
