package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageExeption;
import ru.javawebinar.basejava.exception.NotExistStorageExeption;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.fail;

public abstract class AbstractArrayStorageTest {

    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    void clear() {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }

    @Test
    void save() {
        storage.save(new Resume("uuid4"));
        Assertions.assertEquals(new Resume("uuid4"), storage.get("uuid4"));
    }

    @Test
    void saveExist() {
        ExistStorageExeption thrown = Assertions.assertThrows(ExistStorageExeption.class, () -> {
            storage.save(new Resume("uuid1"));
        }, "ExistStorageExeption должно быть");
    }

    @Test
    void saveOverflow() {
        try {
            for (int i = 0; i < 9997; i++) {
                storage.save(new Resume());
            }

        } catch (StorageException e) {
            fail("Переполнение произошло раньше времени");
        }
    }

    @Test
    void update() {
        storage.update(new Resume(UUID_1));
        Assertions.assertEquals(new Resume(UUID_1), storage.get(UUID_1));
    }

    @Test
    void updateNotExist() {
        NotExistStorageExeption thrown = Assertions.assertThrows(NotExistStorageExeption.class, () -> {
            storage.update(new Resume("sdfsadfdas"));
        });
    }

    @Test
    void get() {
        Assertions.assertEquals(new Resume(UUID_1), storage.get(UUID_1));
    }

    @Test
    public void getNotExist() {
        NotExistStorageExeption thrown = Assertions.assertThrows(NotExistStorageExeption.class, () -> {
            storage.get("dummy");
        });
    }

    @Test
    void delete() {
        storage.delete(UUID_1);
        NotExistStorageExeption thrown = Assertions.assertThrows(NotExistStorageExeption.class, () -> {
            storage.get(UUID_1);
        });
    }

    @Test
    void deleteNotExist() {
        NotExistStorageExeption thrown = Assertions.assertThrows(NotExistStorageExeption.class, () -> {
            storage.delete("dummy");
        });
    }

    @Test
    void getAll() {
        storage.getAll();
        Assertions.assertArrayEquals(new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)},
                storage.getAll());
    }

    @Test
    void size() {
        Assertions.assertEquals(3, storage.size());
    }
}