package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.strategy.StorageStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final StorageStrategy strategy;

    protected PathStorage(String dir, StorageStrategy strategy) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.strategy = strategy;
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path file) {
        return Files.exists(file);
    }

    @Override
    protected void doSave(Resume r, Path file) {
        try {
            Files.createFile(file);
        } catch (IOException e) {
            throw new StorageException("Cant save", file.getFileName().toString(), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected void doDelete(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("Cant delete", file.getFileName().toString(), e);
        }
    }

    @Override
    protected void doUpdate(Resume r, Path file) {
        try {
            strategy.doWrite(r, new BufferedOutputStream(new FileOutputStream(String.valueOf(file))));
        } catch (IOException e) {
            throw new StorageException("Cant update", file.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume doGet(Path file) {
        try {
            return strategy.doRead(new BufferedInputStream(new FileInputStream(String.valueOf(file))));
        } catch (IOException e) {
            throw new StorageException("Path read error", file.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> getResumeList() {
        List<Resume> list = new ArrayList<>();
        for (Path file : collectResumesInList()) {
            list.add(doGet(file));
        }
        return list;
    }

    @Override
    public void clear() {
        collectResumesInList().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return collectResumesInList().size();
    }

    private List<Path> collectResumesInList() {
        try {
            return Files.list(directory).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Cant get", "paths list", e);
        }
    }
}
