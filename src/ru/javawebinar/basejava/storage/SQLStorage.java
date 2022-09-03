package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SQLHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SQLStorage implements Storage {
    public final SQLHelper sqlHelper;

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public SQLStorage(String dbURL, String dbUser, String dbPassword) {
        sqlHelper = new SQLHelper(() -> DriverManager.getConnection(dbURL, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, "
                    + "?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContacts(r, connection);
            insertSections(r, connection);
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            try (PreparedStatement ps = connection.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
                doDelete(ps, r);
            }
            try (PreparedStatement ps = connection.prepareStatement("DELETE FROM section WHERE resume_uuid = ?")) {
                doDelete(ps, r);
            }
            insertContacts(r, connection);
            insertContacts(r, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        List<Resume> resumes = new ArrayList<>();

        sqlHelper.transactionalExecute(connection -> {
           try (PreparedStatement ps = connection.prepareStatement("SELECT * " +
                   "FROM resume r " +
                   "LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                   "WHERE r.uuid = ?")) {
               ps.setString(1, uuid);
               ResultSet rs = ps.executeQuery();
               if (!rs.next()) {
                   throw new NotExistStorageException(uuid);
               }
               Resume r = new Resume(uuid, rs.getString("full_name"));
               doGet(rs, r, this::addContact);
               resumes.add(r);
           }
           return null;
        });
        sqlHelper.transactionalExecute(connection -> {
           try (PreparedStatement ps = connection.prepareStatement("SELECT * " +
                   "FROM resume r " +
                   "LEFT JOIN section s on r.uuid = s.resume_uuid " +
                   "WHERE r.uuid = ?")) {
               ps.setString(1, resumes.get(0).getUuid());
               ResultSet rs = ps.executeQuery();
               if (!rs.next()) {
                   throw new NotExistStorageException(uuid);
               }
               doGet(rs, resumes.get(0), this::addSection);
           }
           return null;
        });
        return resumes.get(0);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted");
        List<Resume> resumes = new ArrayList<>();
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                }
            }
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
                doGetAll(ps, resumes, this::addContact);
            }
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")) {
                doGetAll(ps, resumes, this::addSection);
            }
            return null;
        });
        return resumes;
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            r.addContact(type, value);
        }
    }

    private void addSection(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            switch (type) {
                case PERSONAL, OBJECTIVE -> {
                    r.addSection(type, new TextSection(value));
                }
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    List<String> strings = Arrays.asList(value.split("\n"));
                    r.addSection(type, new ListSection(strings));
                }
            }
        }
    }

    private void insertContacts(Resume r, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, " + "value) " +
                "VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                doInsert(ps, r, e);
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Resume r, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO section (resume_uuid, type, value) " +
                "VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                doInsert(ps, r, e);
            }
            ps.executeBatch();
        }
    }

    private <K,V> void doInsert(PreparedStatement ps, Resume r, Map.Entry<K,V> e) throws SQLException {
        ps.setString(1, r.getUuid());
        ps.setString(2, e.getKey().toString());
        ps.setString(3, e.getValue().toString());
        ps.addBatch();
    }

    private void doGet(ResultSet rs, Resume r, Addable add) throws SQLException {
        do {
            add.addContactOrSection(rs, r);
        } while (rs.next());
    }

    private void doGetAll(PreparedStatement ps, List<Resume> resumes, Addable add) throws SQLException {
        for (Resume r : resumes) {
            ps.setString(1, r.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                add.addContactOrSection(rs, r);
            }
        }
    }

    private void doDelete(PreparedStatement ps, Resume r) throws SQLException {
        ps.setString(1, r.getUuid());
        ps.addBatch();
        ps.executeBatch();
    }

    interface Addable {
        void addContactOrSection(ResultSet rs, Resume r) throws SQLException;
    }


}
