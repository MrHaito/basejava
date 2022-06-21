package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    protected final ArrayList<Resume> STORAGE = new ArrayList<>();

    @Override
    public void clear() {
        STORAGE.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[STORAGE.size()];
        resumes = STORAGE.toArray(resumes);
        return resumes;
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
