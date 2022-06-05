package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Место закончилось");
        } else if (findResumeIndex(r.getUuid()) >= 0) {
            System.out.format("Резюме %s уже есть\n", r.getUuid());
        } else {
            STORAGE[size] = r;
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findResumeIndex(uuid);
        if (index >= 0) {
            STORAGE[index] = STORAGE[size - 1];
            STORAGE[size - 1] = null;
            size--;
        } else {
            System.out.format("Резюме %s не найдено\n", uuid);
        }
    }

    @Override
    protected int findResumeIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (STORAGE[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
