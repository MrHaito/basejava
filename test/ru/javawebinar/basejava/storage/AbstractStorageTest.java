package ru.javawebinar.basejava.storage;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.ResumeTestData;
import ru.javawebinar.basejava.exception.ExistStorageExeption;
import ru.javawebinar.basejava.exception.NotExistStorageExeption;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStorageTest {

    protected static final String STORAGE_DIR = "E:\\Java\\basejava\\storage";

    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String FULLNAME_1 = "James Hetfield";
    private static final String FULLNAME_2 = "Lars Ulrich";
    private static final String FULLNAME_3 = "Kirk Hammett";
    private static final String FULLNAME_4 = "Robert Trujillo";
    private static final String UUID_NOT_EXIST = "dummy";
    private static final Resume RESUME_1 = ResumeTestData.createResume(UUID_1, FULLNAME_1);
    private static final Resume RESUME_2 = ResumeTestData.createResume(UUID_2, FULLNAME_2);
    private static final Resume RESUME_3 = ResumeTestData.createResume(UUID_3, FULLNAME_3);
    private static final Resume RESUME_4 = ResumeTestData.createResume(UUID_4, FULLNAME_4);

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }


    @BeforeEach
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        List<Resume> resumeList = new ArrayList<>();
        Assertions.assertEquals(resumeList, storage.getAllSorted());
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test
    public void saveExist() {
        ExistStorageExeption thrown = Assertions.assertThrows(ExistStorageExeption.class, () -> {
            storage.save(RESUME_1);
        }, "ExistStorageExeption должно быть");
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_1, FULLNAME_1);
        storage.update(resume);
        Assertions.assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test
    public void updateNotExist() {
        NotExistStorageExeption thrown = Assertions.assertThrows(NotExistStorageExeption.class, () -> {
            storage.update(new Resume(UUID_NOT_EXIST));
        });
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    public void getNotExist() {
        NotExistStorageExeption thrown = Assertions.assertThrows(NotExistStorageExeption.class, () -> {
            storage.get(UUID_NOT_EXIST);
        });
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        NotExistStorageExeption thrown = Assertions.assertThrows(NotExistStorageExeption.class, () -> {
            storage.get(UUID_1);
        });
    }

    @Test
    public void deleteNotExist() {
        NotExistStorageExeption thrown = Assertions.assertThrows(NotExistStorageExeption.class, () -> {
            storage.delete(UUID_NOT_EXIST);
        });
    }

    @Test
    public void getAllSorted() {
        List<Resume> resumeList = new ArrayList<>();
        resumeList.add(0, RESUME_1);
        resumeList.add(1, RESUME_3);
        resumeList.add(2, RESUME_2);
        Assertions.assertEquals(resumeList, storage.getAllSorted());
    }

    @Test
    public void size() {
        assertSize(3);
    }

    private void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume.getUuid()));
    }
}