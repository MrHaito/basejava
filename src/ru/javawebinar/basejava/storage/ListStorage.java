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
    public void save(Resume r) {
        isExist(r);
        insertNewResume(r, findResumeIndex(r.getUuid()));
    }

    @Override
    public void delete(String uuid) {
        notExist(uuid);
        deleteResume(findResumeIndex(uuid));
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
    protected int findResumeIndex(String uuid) {
        for (Resume resume : STORAGE) {
            if (resume.getUuid().equals(uuid)) {
                return STORAGE.indexOf(resume);
            }
        }
        return -1;
    }

    @Override
    protected void insertNewResume(Resume r, int index) {
        STORAGE.add(r);
    }

    @Override
    protected void deleteResume(int index) {
        STORAGE.remove(index);
    }

    @Override
    protected void updateResume(int index, Resume r) {
        STORAGE.set(index, r);
    }

    @Override
    protected Resume getResumeByIndex(int index) {
        return STORAGE.get(index);
    }
}
