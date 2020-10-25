package ru.stynyanov.refactoring.model;

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

    public List<String> getAllProducts() {
        return executeDatabaseQuery("SELECT * FROM PRODUCT", rs -> {
            List<String> result = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product(rs.getString("name"), rs.getInt("price"));

                result.add(product.name + "\t" + product.price + "</br>\n");
            }

            return result;
        });
    }

    public List<String> getResultForCommand(String command) {
        return executeDatabaseQuery(sqlQueryForCommand(command), rs -> {
            List<String> result = new ArrayList<>();
            switch (command) {
                case "sum":
                case "count":
                    if (rs.next()) {
                        result.add(rs.getInt(1) + "\n");
                    }
                    break;
                case "max":
                case "min":
                    while (rs.next()) {
                        Product product = new Product(rs.getString("name"), rs.getInt("price"));

                        result.add(product.name + "\t" + product.price + "</br>\n");
                    }
                    break;
            }

            return result;
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
