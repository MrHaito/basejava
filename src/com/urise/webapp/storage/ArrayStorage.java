package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size == storage.length) {
            System.out.println("Место закончилось");
        } else if (findResumeIndex(r.getUuid()) >= 0) {
            System.out.format("Резюме %s уже есть\n", r.getUuid());
        } else {
            storage[size] = r;
            size++;
        }
    }

    public void update(Resume r) {
        int index = findResumeIndex(r.getUuid());
        if (index >= 0) {
            System.out.format("Резюме %s найдено\n", r.getUuid());
            storage[index] = r;
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
        return storage[index];
    }

    public void delete(String uuid) {
        int index = findResumeIndex(uuid);
        if (index >= 0) {
            //System.arraycopy(storage, index + 1, storage, index, resumeCount - 1 - index);
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.format("Резюме %s не найдено\n", uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int findResumeIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
