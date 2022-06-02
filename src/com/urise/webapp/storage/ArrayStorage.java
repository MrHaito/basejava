package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int resumeCount = 0;

    public void clear() {
        if (resumeCount > 0) {
            for (int i = 0; i < resumeCount; i++) {
                storage[i] = null;
            }
            resumeCount = 0;
        } else {
            System.out.println("Список пуст");
        }
    }

    public void save(Resume r) {
        int index = findResumeIndex(r.getUuid());
        if (index >= 0) {
            System.out.format("Резюме %s уже есть\n", r.getUuid());
            return;
        }
        if (resumeCount == storage.length) {
            System.out.println("Место закончилось");
        } else {
            storage[resumeCount] = r;
            resumeCount++;
        }
    }

    public void update(Resume r) {
        int index = findResumeIndex(r.getUuid());
        if (index >= 0) {
            System.out.format("Резюме %s найдено\n", r.getUuid());
        } else {
            System.out.format("Резюме %s не найдено\n", r.getUuid());
        }
    }

    public Resume get(String uuid) {
        int index = findResumeIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            return null;
        }
    }

    public void delete(String uuid) {
        int index = findResumeIndex(uuid);
        if (index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, resumeCount - 1 - index);
            storage[resumeCount - 1] = null;
            resumeCount--;
        } else {
            System.out.format("Резюме %s не найдено\n", uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, resumeCount);
    }

    public int size() {
        return resumeCount;
    }

    private int findResumeIndex(String uuid) {
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
