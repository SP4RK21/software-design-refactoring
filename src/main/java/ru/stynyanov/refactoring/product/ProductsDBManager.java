package ru.stynyanov.refactoring.product;

import ru.stynyanov.refactoring.database.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class ProductsDBManager extends DatabaseManager {
    public ProductsDBManager(String connectionURL) {
        super(connectionURL);
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";
        executeDatabaseUpdate(sql);
    }

    public void addProduct(Product product) {
        String sql = String.format("INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"%s\", \"%d\")", product.name, product.price );
        executeDatabaseUpdate(sql);
    }

    public List<Product> getAllProducts() {
        return executeDatabaseQuery("SELECT * FROM PRODUCT", rs -> {
            List<Product> result = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product(rs.getString("name"), rs.getInt("price"));
                result.add(product);
            }

            return result;
        });
    }

    public Product getProductResultForCommand(String command) {
        return executeDatabaseQuery(sqlQueryForCommand(command), rs -> {
            if (rs.next()) {
                return new Product(rs.getString("name"), rs.getInt("price"));
            }

            return null;
        });
    }

    public int getNumericResultForCommand(String command) {
        return executeDatabaseQuery(sqlQueryForCommand(command), rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;
        });
    }

    private String sqlQueryForCommand(String command) {
        switch (command) {
            case "sum":
                return "SELECT SUM(price) FROM PRODUCT";
            case "max":
                return "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
            case "min":
                return "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
            case "count":
                return "SELECT COUNT(*) FROM PRODUCT";
            default:
                throw new RuntimeException("Unknown command: " + command);
        }
    }
}
