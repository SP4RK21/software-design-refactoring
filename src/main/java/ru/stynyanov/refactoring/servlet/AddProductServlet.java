package ru.stynyanov.refactoring.servlet;

import ru.stynyanov.refactoring.database.DatabaseManager;

import javax.servlet.http.HttpServletRequest;

public class AddProductServlet extends CommonProductServlet {

    public AddProductServlet(DatabaseManager databaseManager) {
        super(databaseManager);
    }

    protected String executeRequest(HttpServletRequest request) {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        String sql = "INSERT INTO PRODUCT " + "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
        databaseManager.executeDatabaseUpdate(sql);

        return "OK";
    }
}
