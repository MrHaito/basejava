package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapStorageAlternate extends AbstractStorage{
    protected final Map<Integer, Resume> STORAGE = new HashMap<>();
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
        return uuid.hashCode();
    }

    @Override
    protected boolean isExist(Object object) {
        if (object == null) {
            return false;
        }
        return STORAGE.containsKey((Integer) object);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        STORAGE.put((Integer) searchKey, r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        STORAGE.remove(searchKey);
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        STORAGE.put((Integer) searchKey, r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return STORAGE.get((Integer) searchKey);
    }
}
