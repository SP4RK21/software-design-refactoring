package ru.stynyanov.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.stynyanov.refactoring.product.ProductsDBManager;
import ru.stynyanov.refactoring.servlet.AddProductServlet;
import ru.stynyanov.refactoring.servlet.GetProductsServlet;
import ru.stynyanov.refactoring.servlet.QueryServlet;

public class Main {
    private static final String DB_CONNECTION_URL = "jdbc:sqlite:test.db";
    private static final int SERVER_PORT = 8081;

    public static void main(String[] args) throws Exception {
        ProductsDBManager dbManager = new ProductsDBManager(DB_CONNECTION_URL);
        dbManager.createTable();

        Server server = new Server(SERVER_PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(dbManager)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(dbManager)), "/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(dbManager)), "/query");

        server.start();
        server.join();
    }
}
