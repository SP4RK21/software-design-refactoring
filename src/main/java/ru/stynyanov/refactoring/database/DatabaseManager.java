package ru.stynyanov.refactoring.database;

import java.sql.*;
import java.util.List;

public class DatabaseManager {
    private final String connectionURL;

    public DatabaseManager(String connectionURL) {
        this.connectionURL = connectionURL;
    }

    protected void executeDatabaseUpdate(String sqlUpdateRequest) {
        try {
            try (Connection c = DriverManager.getConnection(connectionURL)) {
                Statement stmt = c.createStatement();
                stmt.executeUpdate(sqlUpdateRequest);
                stmt.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected List<String> executeDatabaseQuery(String sqlQueryRequest, ResultSetHandler rsHandler) {
        List<String> result;
        try {
            try (Connection c = DriverManager.getConnection(connectionURL)) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(sqlQueryRequest);
                result = rsHandler.handle(rs);

                rs.close();
                stmt.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
