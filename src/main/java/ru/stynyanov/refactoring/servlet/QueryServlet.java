package ru.stynyanov.refactoring.servlet;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class QueryServlet extends CommonProductServlet {

    protected String executeRequest(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder("<html><body>\n");
        String command = request.getParameter("command");
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(sqlQueryForCommand(command));
                stringBuilder.append(htmlTitleForCommand(command));

                switch (command) {
                    case "sum":
                    case "count":
                        if (rs.next()) {
                            stringBuilder.append(rs.getInt(1)).append("\n");
                        }
                        break;
                    case "max":
                    case "min":
                        while (rs.next()) {
                            String name = rs.getString("name");
                            int price = rs.getInt("price");

                            stringBuilder.append(name).append("\t").append(price).append("</br>\n");
                        }
                        break;
                }

                rs.close();
                stmt.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        stringBuilder.append("</body></html>");
        return stringBuilder.toString();
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
