package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapStorageAlternate extends AbstractStorage{
    protected final Map<String, Resume> STORAGE = new HashMap<>();
    @Override
    public void clear() {
        STORAGE.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
        List<Resume> sortedStorage = new ArrayList<>(STORAGE.values());
        sortedStorage.sort(RESUME_COMPARATOR);
        return sortedStorage;
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
}
