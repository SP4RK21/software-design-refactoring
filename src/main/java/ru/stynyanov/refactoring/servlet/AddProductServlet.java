package ru.stynyanov.refactoring.servlet;

import ru.stynyanov.refactoring.html.HTMLBuilder;
import ru.stynyanov.refactoring.product.Product;
import ru.stynyanov.refactoring.product.ProductsDBManager;

import javax.servlet.http.HttpServletRequest;

public class AddProductServlet extends CommonProductServlet {

    public AddProductServlet(ProductsDBManager databaseManager) {
        super(databaseManager);
    }

    protected String executeRequest(HttpServletRequest request) {
        Product product = new Product(request.getParameter("name"), Integer.parseInt(request.getParameter("price")));
        databaseManager.addProduct(product);

        return new HTMLBuilder().addTextWithNewLine("OK").build();
    }
}
