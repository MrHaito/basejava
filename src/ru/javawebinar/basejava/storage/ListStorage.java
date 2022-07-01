package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected final List<Resume> STORAGE = new ArrayList<>();

    @Override
    public void clear() {
        STORAGE.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
        List<Resume> sortedStorage = new ArrayList<>(STORAGE);
        sortedStorage.sort(RESUME_COMPARATOR);
        return sortedStorage;
    }

    @Override
    public int size() {
        return STORAGE.size();
    }

    @Override
    protected Object findSearchKey(String uuid) {
        for (Resume resume : STORAGE) {
            if (resume.getUuid().equals(uuid)) {
                return STORAGE.indexOf(resume);
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object object) {
        return (int) object >= 0;
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        STORAGE.add(r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        STORAGE.remove((int) searchKey);
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        STORAGE.set((int) searchKey, r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return STORAGE.get((int) searchKey);
    }
}
