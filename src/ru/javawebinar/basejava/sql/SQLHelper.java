package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageExeption;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLHelper {

    public final ConnectionFactory connectionFactory;

    public SQLHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute (String sql) {
        execute(sql, PreparedStatement::execute);
    }

    public <T> T execute(String sql, SQLHelperStrategy<T> sqlStrategy) {
        try (Connection connection = connectionFactory.getConnection(); PreparedStatement ps =
                connection.prepareStatement(sql)) {
            return sqlStrategy.execute(ps);
        }
        catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageExeption("Resume already exist");
            }
            throw new StorageException(e);
        }
    }
}
