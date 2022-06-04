package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

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
            STORAGE[size] = r;
            size++;
        }
    }

    public void update(Resume r) {
        int index = findResumeIndex(r.getUuid());
        if (index >= 0) {
            System.out.format("Резюме %s найдено\n", r.getUuid());
            STORAGE[index] = r;
        } else {
            System.out.format("Резюме %s не найдено\n", r.getUuid());
        }
    }

    public void delete(String uuid) {
        int index = findResumeIndex(uuid);
        if (index >= 0) {
            //System.arraycopy(storage, index + 1, storage, index, resumeCount - 1 - index);
            STORAGE[index] = STORAGE[size - 1];
            STORAGE[size - 1] = null;
            size--;
        } else {
            System.out.format("Резюме %s не найдено\n", uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(STORAGE, size);
    }

    protected int findResumeIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (STORAGE[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
