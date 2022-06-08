package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] STORAGE = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(STORAGE, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Место закончилось");
        } else if (findResumeIndex(r.getUuid()) >= 0) {
            System.out.format("Резюме %s уже есть\n", r.getUuid());
        } else {
            insertNewResume(r, findResumeIndex(r.getUuid()));
            size++;
        }
    }

    public void update(Resume r) {
        int index = findResumeIndex(r.getUuid());
        if (index >= 0) {
            System.out.format("Резюме %s найдено и обновлено\n", r.getUuid());
            STORAGE[index] = r;
        } else {
            System.out.format("Резюме %s не найдено\n", r.getUuid());
        }
    }

    public Resume get(String uuid) {
        int index = findResumeIndex(uuid);
        if (index == -1) {
            System.out.format("Резюме %s не найдено\n", uuid);
            return null;
        }
        return STORAGE[index];
    }

    @Override
    public void delete(String uuid) {
        int index = findResumeIndex(uuid);
        if (index >= 0) {
            deleteResume(index);
            size--;
        } else {
            System.out.format("Резюме %s не найдено\n", uuid);
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(STORAGE, size);
    }

    public int size() {
        return size;
    }

    protected abstract int findResumeIndex(String uuid);

    protected abstract void insertNewResume(Resume r, int index);

    protected abstract void deleteResume(int index);


}
