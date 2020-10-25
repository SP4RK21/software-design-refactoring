package ru.stynyanov.refactoring.servlet;

import ru.stynyanov.refactoring.html.HTMLBuilder;
import ru.stynyanov.refactoring.product.Product;
import ru.stynyanov.refactoring.product.ProductsDBManager;

import javax.servlet.http.HttpServletRequest;

public class QueryServlet extends CommonProductServlet {

    public QueryServlet(ProductsDBManager databaseManager) {
        super(databaseManager);
    }

    protected String executeRequest(HttpServletRequest request) {
        String command = request.getParameter("command");
        HTMLBuilder htmlBuilder = new HTMLBuilder();
        htmlBuilder.addHeader(htmlTitleForCommand(command));

        if (command.equals("min") || command.equals("max")) {
            Product product = databaseManager.getProductResultForCommand(command);
            if (product != null) {
                htmlBuilder.addTextWithNewLine(product.name + "\t" + product.price);
            } else {
                htmlBuilder.addTextWithNewLine("No products in database");
            }
        } else {
            htmlBuilder.addTextWithNewLine(String.valueOf(databaseManager.getNumericResultForCommand(command)));
        }

        return htmlBuilder.build();
    }

    private String htmlTitleForCommand(String command) {
        switch (command) {
            case "sum":
                return "Summary price:";
            case "max":
                return "Product with max price:";
            case "min":
                return "Product with min price:";
            case "count":
                return "Number of products:";
            default:
                return "Unknown command: " + command;
        }
    }
}
