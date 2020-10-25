package ru.stynyanov.refactoring.servlet;

import ru.stynyanov.refactoring.database.DatabaseManager;
import ru.stynyanov.refactoring.model.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class GetProductsServlet extends CommonProductServlet {

    public GetProductsServlet(DatabaseManager databaseManager) {
        super(databaseManager);
    }

    protected String executeRequest(HttpServletRequest request) {
        List<String> products = databaseManager.executeDatabaseQuery("SELECT * FROM PRODUCT", rs -> {
            List<String> result = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product(rs.getString("name"), rs.getInt("price"));

                result.add(product.name + "\t" + product.price + "</br>\n");
            }

            return result;
        });

        return "<html><body>\n" + String.join("\n", products) + "</body></html>\n";
    }
}
