package ru.stynyanov.refactoring.servlet;

import ru.stynyanov.refactoring.database.DatabaseManager;
import ru.stynyanov.refactoring.model.Product;

import javax.servlet.http.HttpServletRequest;

public class AddProductServlet extends CommonProductServlet {

    public AddProductServlet(DatabaseManager databaseManager) {
        super(databaseManager);
    }

    protected String executeRequest(HttpServletRequest request) {
        Product product = new Product(request.getParameter("name"), Integer.parseInt(request.getParameter("price")));
        String sql = String.format("INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"%s\", \"%d\")", product.name, product.price );
        databaseManager.executeDatabaseUpdate(sql);

        return "OK";
    }
}
