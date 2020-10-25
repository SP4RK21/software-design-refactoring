package ru.stynyanov.refactoring.servlet;

import ru.stynyanov.refactoring.model.ProductsDBManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class QueryServlet extends CommonProductServlet {

    public QueryServlet(ProductsDBManager databaseManager) {
        super(databaseManager);
    }

    protected String executeRequest(HttpServletRequest request) {
        String command = request.getParameter("command");
        List<String> products = databaseManager.getResultForCommand(command);

        return "<html><body>\n" + htmlTitleForCommand(command) + String.join("\n", products) + "</body></html>\n";
    }

    private String htmlTitleForCommand(String command) {
        switch (command) {
            case "sum":
                return "Summary price: \n";
            case "max":
                return "<h1>Product with max price: </h1>\n";
            case "min":
                return "<h1>Product with min price: </h1>\n";
            case "count":
                return "Number of products: \n";
            default:
                return "Unknown command: " + command + "\n";
        }
    }

}
