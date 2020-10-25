package ru.stynyanov.refactoring.servlet;

import ru.stynyanov.refactoring.database.DatabaseManager;

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
                String name = rs.getString("name");
                int price = rs.getInt("price");

                result.add(name + "\t" + price + "</br>\n");
            }

            return result;
        });

        return "<html><body>\n" + String.join("\n", products) + "</body></html>\n";
    }
}
