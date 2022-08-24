package ru.javawebinar.basejava.storage.strategy;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SQLHelperStrategy<T> {
    T execute (PreparedStatement ps) throws SQLException;
}
