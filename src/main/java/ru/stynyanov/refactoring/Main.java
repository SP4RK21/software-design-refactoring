package ru.stynyanov.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.stynyanov.refactoring.database.DatabaseManager;
import ru.stynyanov.refactoring.servlet.AddProductServlet;
import ru.stynyanov.refactoring.servlet.GetProductsServlet;
import ru.stynyanov.refactoring.servlet.QueryServlet;

public class Main {
    private static final String DB_CONNECTION_URL = "jdbc:sqlite:test.db";
    private static final int SERVER_PORT = 8081;

    public static void main(String[] args) throws Exception {
        DatabaseManager manager = new DatabaseManager(DB_CONNECTION_URL);
        String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";
        manager.executeDatabaseUpdate(sql);

        Server server = new Server(SERVER_PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(manager)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(manager)), "/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(manager)), "/query");

        server.start();
        server.join();
    }
}
