package ru.javawebinar.basejava.exception;

public class ExistStorageExeption extends StorageException {
    public ExistStorageExeption(String uuid) {
        super("Резюме " + uuid + " уже есть", uuid);
    }
}
