package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Место закончилось");
        } else if (findResumeIndex(r.getUuid()) >= 0) {
            System.out.format("Резюме %s уже есть\n", r.getUuid());
        } else {
            if (size == 0) {
                STORAGE[size] = r;
            } else {
                int index = findIndexForSave(r);
                System.arraycopy(STORAGE, index, STORAGE, index + 1, size - index);
                STORAGE[index] = r;
            }
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findResumeIndex(uuid);
        if (index >= 0) {
            System.arraycopy(STORAGE, index + 1, STORAGE, index, size - 1 - index);
            size--;
        } else {
            System.out.format("Резюме %s не найдено\n", uuid);
        }
    }

    @Override
    protected int findResumeIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(STORAGE, 0, size, searchKey);
    }

    private int findIndexForSave(Resume resume) {
        if (resume.getUuid().compareTo(STORAGE[size - 1].getUuid()) > 0) {
            return size;
        } else {
            for (int i = 0; i <= size; i++) {
                if (resume.getUuid().compareTo(STORAGE[i].getUuid()) > 0 &&
                        resume.getUuid().compareTo(STORAGE[i + 1].getUuid()) < 0) {
                    return i + 1;
                }
            }
        }
        return 0;
    }
}
