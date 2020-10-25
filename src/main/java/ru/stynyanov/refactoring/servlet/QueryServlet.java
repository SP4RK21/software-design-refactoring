package ru.stynyanov.refactoring.servlet;

import ru.stynyanov.refactoring.database.DatabaseManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class QueryServlet extends CommonProductServlet {

    public QueryServlet(DatabaseManager databaseManager) {
        super(databaseManager);
    }

    protected String executeRequest(HttpServletRequest request) {
        String command = request.getParameter("command");

        List<String> products = databaseManager.executeDatabaseQuery(sqlQueryForCommand(command), rs -> {
            List<String> result = new ArrayList<>();
            switch (command) {
                case "sum":
                case "count":
                    if (rs.next()) {
                        result.add(rs.getInt(1) + "\n");
                    }
                    break;
                case "max":
                case "min":
                    while (rs.next()) {
                        String name = rs.getString("name");
                        int price = rs.getInt("price");

                        result.add(name + "\t" + price + "</br>\n");
                    }
                    break;
            }

            return result;
        });

        return "<html><body>\n" + htmlTitleForCommand(command) + String.join("\n", products) + "</body></html>\n";
    }

    private String sqlQueryForCommand(String command) {
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

    private String htmlTitleForCommand(String command) {
        switch (command) {
            case "sum":
                return "SELECT SUM(price) FROM PRODUCT";
            case "max":
                return "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
            case "min":
                return "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
            case "count":
                return "SELECT COUNT(*) FROM PRODUCT";
            default:
                throw new RuntimeException("Unknown command: " + command);
        }
    }

}
