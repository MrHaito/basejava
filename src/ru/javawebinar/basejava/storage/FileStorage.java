package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.strategy.StorageStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final StorageStrategy strategy;

    protected FileStorage(String dir, StorageStrategy strategy) {
        File directory = new File(dir);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = Objects.requireNonNull(directory, "directory must not be null");
        this.strategy = strategy;
    }

    @Override
    protected File findSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Cant save", file.getName(), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Cant delete", file.getName());
        }
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            strategy.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Cant update", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return strategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> getResumeList() {
        List<Resume> list = new ArrayList<>();
        try {
            for (File file : collectResumesInArray()) {
                list.add(strategy.doRead(new BufferedInputStream(new FileInputStream(file))));
            }
        } catch (IOException e) {
            throw new StorageException("Cant get", "storage", e);
        }
        return list;
    }

    @Override
    public void clear() {
        if (collectResumesInArray() != null) {
            for (File file : collectResumesInArray()) {
                doDelete(file);
            }
        } else {
            throw new StorageException("Cant clear", "storage");
        }
    }

    @Override
    public int size() {
        if (collectResumesInArray() != null) {
            return collectResumesInArray().length;
        }
        return 0;
    }

    private File[] collectResumesInArray() {
        return directory.listFiles();
    }
}
