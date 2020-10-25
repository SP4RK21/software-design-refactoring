package ru.stynyanov.refactoring.servlet;

import ru.stynyanov.refactoring.model.ProductsDBManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GetProductsServlet extends CommonProductServlet {

    public GetProductsServlet(ProductsDBManager databaseManager) {
        super(databaseManager);
    }

    protected String executeRequest(HttpServletRequest request) {
        List<String> products = databaseManager.getAllProducts();

        return "<html><body>\n" + String.join("\n", products) + "</body></html>\n";
    }
}
