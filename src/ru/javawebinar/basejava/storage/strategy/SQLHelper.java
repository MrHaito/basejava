package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.exception.ExistStorageExeption;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLHelper {

    public final ConnectionFactory connectionFactory;

    public SQLHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
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
