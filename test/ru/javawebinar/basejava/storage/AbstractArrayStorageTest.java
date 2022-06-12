package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageExeption;
import ru.javawebinar.basejava.exception.NotExistStorageExeption;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;

public abstract class AbstractArrayStorageTest {

    private final Storage STORAGE;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_NOT_EXIST = "dummy";
    final Resume RESUME_1 = new Resume(UUID_1);
    final Resume RESUME_2 = new Resume(UUID_2);
    final Resume RESUME_3 = new Resume(UUID_3);
    final Resume RESUME_4 = new Resume(UUID_4);

    public AbstractArrayStorageTest(Storage storage) {
        this.STORAGE = storage;
    }

    @BeforeEach
    public void setUp() throws Exception {
        STORAGE.clear();
        STORAGE.save(RESUME_1);
        STORAGE.save(RESUME_2);
        STORAGE.save(RESUME_3);
    }

    @Test
    void clear() {
        STORAGE.clear();
        assertSize(0);
        Assertions.assertArrayEquals(new Resume[]{}, STORAGE.getAll());
    }

    @Test
    void save() {
        STORAGE.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test
    void saveExist() {
        ExistStorageExeption thrown = Assertions.assertThrows(ExistStorageExeption.class, () -> {
            STORAGE.save(RESUME_1);
        }, "ExistStorageExeption должно быть");
    }

    @Test
    void saveOverflow() throws NoSuchFieldException, IllegalAccessException {
        STORAGE.clear();
        Field storageLimit = AbstractArrayStorage.class.getDeclaredField("STORAGE_LIMIT");
        int storageLenght = storageLimit.getInt(storageLimit);
        try {
            for (int i = 0; i < storageLenght; i++) {
                STORAGE.save(new Resume());
            }
        } catch (StorageException e) {
            fail("Переполнение произошло раньше времени");
        }
        StorageException thrown = Assertions.assertThrows(StorageException.class, () -> {
            STORAGE.save(RESUME_4);
        });
    }

    @Test
    void update() {
        Resume resume = RESUME_1;
        STORAGE.update(resume);
        Assertions.assertSame(resume, STORAGE.get(resume.getUuid()));
    }

    @Test
    void updateNotExist() {
        NotExistStorageExeption thrown = Assertions.assertThrows(NotExistStorageExeption.class, () -> {
            STORAGE.update(new Resume(UUID_NOT_EXIST));
        });
    }

    @Test
    void get() {
        Assertions.assertTrue(assertGet(RESUME_1));
        Assertions.assertTrue(assertGet(RESUME_2));
        Assertions.assertTrue(assertGet(RESUME_3));
    }

    @Test
    public void getNotExist() {
        NotExistStorageExeption thrown = Assertions.assertThrows(NotExistStorageExeption.class, () -> {
            STORAGE.get(UUID_NOT_EXIST);
        });
    }

    @Test
    void delete() {
        STORAGE.delete(UUID_1);
        NotExistStorageExeption thrown = Assertions.assertThrows(NotExistStorageExeption.class, () -> {
            STORAGE.get(UUID_1);
        });
    }

    @Test
    void deleteNotExist() {
        NotExistStorageExeption thrown = Assertions.assertThrows(NotExistStorageExeption.class, () -> {
            STORAGE.delete(UUID_NOT_EXIST);
        });
    }

    @Test
    void getAll() {
        STORAGE.getAll();
        Assertions.assertArrayEquals(new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)},
                STORAGE.getAll());
    }

    @Test
    void size() {
        Assertions.assertTrue(assertSize(3));
    }

    private boolean assertSize(int size) {
        return size == STORAGE.size();
    }

    private boolean assertGet(Resume resume) {
        return resume == STORAGE.get(resume.getUuid());
    }
}