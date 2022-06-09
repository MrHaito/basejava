package ru.javawebinar.basejava.exception;

public class NotExistStorageExeption extends StorageException {
    public NotExistStorageExeption(String uuid) {
        super("Резюме " + uuid + " не найдено", uuid);
    }
}
