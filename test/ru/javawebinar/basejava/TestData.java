package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.util.UUID;

public class TestData {
    protected static final File STORAGE_DIR = Config.getInstance().getStorageDir();

    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();
    public static final String FULLNAME_1 = "James Hetfield";
    public static final String FULLNAME_2 = "Lars Ulrich";
    public static final String FULLNAME_3 = "Kirk Hammett";
    public static final String FULLNAME_4 = "Robert Trujillo";
    public static final String UUID_NOT_EXIST = "dummy";
    public static final Resume RESUME_1 = ResumeTestData.createResume(UUID_1, FULLNAME_1);
    public static final Resume RESUME_2 = ResumeTestData.createResume(UUID_2, FULLNAME_2);
    public static final Resume RESUME_3 = ResumeTestData.createResume(UUID_3, FULLNAME_3);
    public static final Resume RESUME_4 = ResumeTestData.createResume(UUID_4, FULLNAME_4);

}
