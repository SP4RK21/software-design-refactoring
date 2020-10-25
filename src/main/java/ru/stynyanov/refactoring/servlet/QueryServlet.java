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

        if ("max".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
                    stringBuilder.append("<h1>Product with max price: </h1>\n");

                    while (rs.next()) {
                        String  name = rs.getString("name");
                        int price  = rs.getInt("price");

                        stringBuilder.append(name).append("\t").append(price).append("</br>\n");
                    }

                    rs.close();
                    stmt.close();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
                    stringBuilder.append("<h1>Product with min price: </h1>\n");

                    while (rs.next()) {
                        String  name = rs.getString("name");
                        int price  = rs.getInt("price");

                        stringBuilder.append(name).append("\t").append(price).append("</br>\n");
                    }

                    rs.close();
                    stmt.close();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT SUM(price) FROM PRODUCT");
                    stringBuilder.append("Summary price: \n");

                    if (rs.next()) {
                        stringBuilder.append(rs.getInt(1)).append("\n");
                    }

                    rs.close();
                    stmt.close();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRODUCT");
                    stringBuilder.append("Number of products: \n");

                    if (rs.next()) {
                        stringBuilder.append(rs.getInt(1)).append("\n");
                    }

                    rs.close();
                    stmt.close();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            stringBuilder.append("Unknown command: ").append(command);
        }

        stringBuilder.append("</body></html>");
        return stringBuilder.toString();
    }

}
