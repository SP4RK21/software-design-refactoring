package ru.stynyanov.refactoring.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@FunctionalInterface
public interface ResultSetHandler {
    List<String> handle(ResultSet rs) throws SQLException;
}
