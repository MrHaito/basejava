package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageExeption;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.strategy.SQLHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class SQLStorage implements Storage {
    public final SQLHelper sqlHelper;

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public SQLStorage(String dbURL, String dbUser, String dbPassword) {
        sqlHelper = new SQLHelper(() -> DriverManager.getConnection(dbURL, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?, ?)", ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        sqlHelper.execute("UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageExeption(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.execute("SELECT * FROM resume r WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet resultSet = ps.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageExeption(uuid);
            }
            return new Resume(uuid, resultSet.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageExeption(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted");
        List<Resume> resumes = new ArrayList<>();
        Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
        return sqlHelper.execute("SELECT * FROM resume", ps -> {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                resumes.add(new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
            }
            resumes.sort(RESUME_COMPARATOR);
            return resumes;
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        });
    }
}
