package ru.stynyanov.refactoring.servlet;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GetProductsServlet extends CommonProductServlet {

    protected String executeRequest(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");
                stringBuilder.append("<html><body>\n");

                while (rs.next()) {
                    String  name = rs.getString("name");
                    int price  = rs.getInt("price");

                    stringBuilder.append(name).append("\t").append(price).append("</br>\n");
                }

                stringBuilder.append("</body></html>\n");

                rs.close();
                stmt.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return stringBuilder.toString();
    }
}
