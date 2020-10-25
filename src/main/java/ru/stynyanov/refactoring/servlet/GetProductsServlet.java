package ru.stynyanov.refactoring.servlet;

import ru.stynyanov.refactoring.model.Product;
import ru.stynyanov.refactoring.model.ProductsDBManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GetProductsServlet extends CommonProductServlet {

    public GetProductsServlet(ProductsDBManager databaseManager) {
        super(databaseManager);
    }

    protected String executeRequest(HttpServletRequest request) {
        List<Product> products = databaseManager.getAllProducts();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><body>\n");
        for (Product product : products) {
            stringBuilder.append(product.name).append("\t").append(product.price).append("\n");
        }
        stringBuilder.append("</body></html>\n");

        return stringBuilder.toString();
    }
}
