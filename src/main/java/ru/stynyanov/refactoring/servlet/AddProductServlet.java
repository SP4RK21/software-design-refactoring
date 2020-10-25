package ru.stynyanov.refactoring.servlet;

import ru.stynyanov.refactoring.model.Product;
import ru.stynyanov.refactoring.model.ProductsDBManager;

import javax.servlet.http.HttpServletRequest;

public class AddProductServlet extends CommonProductServlet {

    public AddProductServlet(ProductsDBManager databaseManager) {
        super(databaseManager);
    }

    protected String executeRequest(HttpServletRequest request) {
        Product product = new Product(request.getParameter("name"), Integer.parseInt(request.getParameter("price")));
        databaseManager.addProduct(product);

        return "OK";
    }
}
