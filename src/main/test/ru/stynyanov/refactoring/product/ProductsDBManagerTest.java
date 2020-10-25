package ru.stynyanov.refactoring.product;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProductsDBManagerTest {

    private ProductsDBManager dbManager;

    private static final Product[] TEST_PRODUCTS = {
            new Product("iPhone 12 Pro", 99990),
            new Product("iPhone 12 Pro Max", 109990),
            new Product("iPhone SE", 39990)
    };

    @Before
    public void setupDB() {
        final String TEST_CONNECTION_URL = "jdbc:sqlite:testManager.db";

        dbManager = new ProductsDBManager(TEST_CONNECTION_URL);
        dbManager.executeDatabaseUpdate("DROP TABLE IF EXISTS PRODUCT");
        dbManager.createTable();
        for (Product product : TEST_PRODUCTS) {
            dbManager.addProduct(product);
        }
    }

    @Test
    public void testGetAllProducts() {
        final List<Product> products = dbManager.getAllProducts();
        assertEquals(TEST_PRODUCTS.length, products.size());
        assertTrue(Arrays.asList(TEST_PRODUCTS).containsAll(products));
    }

    @Test
    public void testAddProduct() {
        final Product newProduct = new Product("iPhone 12 Mini", 69990);
        dbManager.addProduct(newProduct);
        final List<Product> products = dbManager.getAllProducts();
        assertTrue(products.contains(newProduct));
    }

    @Test
    public void testCountQuery() {
        assertEquals(TEST_PRODUCTS.length, dbManager.getProductsCount());
    }

    @Test
    public void testSumQuery() {
        long expectedSum = 0;
        for (final Product product : TEST_PRODUCTS) {
            expectedSum += product.price;
        }
        assertEquals(expectedSum, dbManager.getPricesSum());
    }

    @Test
    public void testMinQuery() {
        Integer minPrice = null;
        Product expectedProduct = null;
        for (final Product product : TEST_PRODUCTS) {
            if (minPrice == null || minPrice > product.price) {
                minPrice = product.price;
                expectedProduct = product;
            }
        }
        assertEquals(expectedProduct, dbManager.getMinPriceProduct());
    }

    @Test
    public void testMaxQuery() {
        Integer maxPrice = null;
        Product expectedProduct = null;
        for (final Product product : TEST_PRODUCTS) {
            if (maxPrice == null || maxPrice < product.price) {
                maxPrice = product.price;
                expectedProduct = product;
            }
        }
        assertEquals(expectedProduct, dbManager.getMaxPriceProduct());
    }
}
