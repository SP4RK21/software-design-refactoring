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
        String sql = String.format("INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"%s\", \"%d\")", product.name, product.price);
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

    public Product getMaxPriceProduct() {
        return getProductResultForCommand("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
    }

    public Product getMinPriceProduct() {
        return getProductResultForCommand("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
    }

    public int getPricesSum() {
        return getNumericResultForCommand("SELECT SUM(price) FROM PRODUCT");
    }

    public int getProductsCount() {
        return getNumericResultForCommand("SELECT COUNT(*) FROM PRODUCT");
    }

    private Product getProductResultForCommand(String sqlCommand) {
        return executeDatabaseQuery(sqlCommand, rs -> {
            if (rs.next()) {
                return new Product(rs.getString("name"), rs.getInt("price"));
            }

            return null;
        });
    }

    private int getNumericResultForCommand(String sqlCommand) {
        return executeDatabaseQuery(sqlCommand, rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;
        });
    }
}
