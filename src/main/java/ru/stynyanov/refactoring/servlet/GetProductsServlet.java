package ru.stynyanov.refactoring.servlet;

import ru.stynyanov.refactoring.html.HTMLBuilder;
import ru.stynyanov.refactoring.product.Product;
import ru.stynyanov.refactoring.product.ProductsDBManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GetProductsServlet extends CommonProductServlet {

    public GetProductsServlet(ProductsDBManager databaseManager) {
        super(databaseManager);
    }

    protected String executeRequest(HttpServletRequest request) {
        List<Product> products = databaseManager.getAllProducts();
        HTMLBuilder htmlBuilder = new HTMLBuilder();
        for (Product product : products) {
            htmlBuilder.addTextWithNewLine(product.name + "\t" + product.price);
        }

        return htmlBuilder.build();
    }
}
