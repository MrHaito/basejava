package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return Paths.get(uuid);
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
            throw new StorageException("IO error", file.getFileName().toString(), e);
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
            doWrite(r, new BufferedOutputStream(new FileOutputStream(String.valueOf(file))));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume doGet(Path file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(String.valueOf(file))));
        } catch (IOException e) {
            throw new StorageException("Path read error", file.getFileName().toString(), e);
        }
    }
//
//    @Override
//    protected List<Resume> getResumeList() {
//        List<Resume> list = new ArrayList<>();
//        Path[] fileList = directory.listFiles();
//        try {
//            for (Path file : fileList) {
//                list.add(doRead(new BufferedInputStream(new FileInputStream(file))));
//            }
//        } catch (IOException e) {
//            throw new StorageException("Cant get", "storage", e);
//        }
//        return list;
//    }
//
    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }
//
//    @Override
//    public int size() {
//        Path[] list = directory.listFiles();
//        if (list != null) {
//            return list.length;
//        }
//        return 0;
//    }

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;
}
