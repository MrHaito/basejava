import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int resumeCount = 0;

    void clear() {
        if (resumeCount > 0) {
            for (int i = 0; i < resumeCount; i++) {
                storage[i] = null;
            }
            resumeCount = 0;
        } else {
            System.out.println("Список пуст");
        }
    }

    void save(Resume r) {
        int index = findResumeIndex(r.uuid);
        if (index >= 0) {
            System.out.println("Резюме с таким uuid уже есть");
            return;
        }
        if (resumeCount == storage.length) {
            System.out.println("Место закончилось");
        } else {
            storage[resumeCount] = r;
            resumeCount++;
        }
    }

    Resume get(String uuid) {
        int index = findResumeIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            return null;
        }
    }

    void delete(String uuid) {
        int index = findResumeIndex(uuid);
        if (index >= 0) {
            System.arraycopy(storage,  index + 1, storage, index, resumeCount - 1 - index);
            storage[resumeCount - 1] = null;
            resumeCount--;
        } else {
            System.out.format("Резюме %s не найдено\n", uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, resumeCount);
    }

    int size() {
        return resumeCount;
    }

    private int findResumeIndex(String uuid) {
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
