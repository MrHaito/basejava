package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.util.List;

public class ObjectPathStorage extends AbstractPathStorage {
    protected ObjectPathStorage(String dir) {
        super(dir);
    }

    //    Тут пока методы из ObjectStreamStorage
    @Override
    protected void doWrite(Resume r, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(r);
        }
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        try (ObjectInput ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }

    @Override
    protected List<Resume> getResumeList() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
