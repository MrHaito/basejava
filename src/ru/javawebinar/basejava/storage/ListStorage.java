package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    protected final List<Resume> STORAGE = new ArrayList<>();

    @Override
    public void clear() {
        STORAGE.clear();
    }

    @Override
    public int size() {
        return STORAGE.size();
    }

    @Override
    protected Integer findSearchKey(String uuid) {
        for (Resume resume : STORAGE) {
            if (resume.getUuid().equals(uuid)) {
                return STORAGE.indexOf(resume);
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer object) {
        return object >= 0;
    }

    @Override
    protected void doSave(Resume r, Integer searchKey) {
        STORAGE.add(r);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        STORAGE.remove((int) searchKey);
    }

    @Override
    protected void doUpdate(Resume r, Integer searchKey) {
        STORAGE.set(searchKey, r);
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return STORAGE.get(searchKey);
    }

    @Override
    protected List<Resume> getResumeList() {
        return new ArrayList<>(STORAGE);
    }
}
