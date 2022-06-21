package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageExeption;
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
//        if (getSearchKey(r.getUuid()) >= 0) {
//            throw new ExistStorageExeption(r.getUuid());
//        } else {
//            insertNewResume(r, getSearchKey(r.getUuid()));
//        }
        isExist(r);
        insertNewResume(r, getSearchKey(r.getUuid()));
    }

    @Override
    public void update(Resume r) {
        int index = getSearchKey(r.getUuid());
        if (index >= 0) {
            System.out.format("Резюме %s найдено и обновлено\n", r.getUuid());
            STORAGE.set(index, r);
        } else {
            throw new NotExistStorageExeption(r.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = getSearchKey(uuid);
        if (index == -1) {
            throw new NotExistStorageExeption(uuid);
        }
        return STORAGE.get(index);
    }

    @Override
    public void delete(String uuid) {
        int index = getSearchKey(uuid);
        if (index >= 0) {
            deleteResume(index);
        } else {
            throw new NotExistStorageExeption(uuid);
        }
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
    protected int getSearchKey(String uuid) {
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


}
