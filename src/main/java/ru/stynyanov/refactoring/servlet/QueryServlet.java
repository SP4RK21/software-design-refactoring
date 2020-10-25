package ru.stynyanov.refactoring.servlet;

import ru.stynyanov.refactoring.html.HTMLBuilder;
import ru.stynyanov.refactoring.product.Product;
import ru.stynyanov.refactoring.product.ProductsDBManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class QueryServlet extends CommonProductServlet {

    private static final String[] SUPPORTED_COMMANDS = {"max", "min", "sum", "count"};

    public QueryServlet(ProductsDBManager databaseManager) {
        super(databaseManager);
    }

    protected String executeRequest(HttpServletRequest request) {
        String command = request.getParameter("command");
        HTMLBuilder htmlBuilder = new HTMLBuilder();
        htmlBuilder.addHeader(htmlTitleForCommand(command));
        if (!Arrays.asList(SUPPORTED_COMMANDS).contains(command)) {
            return htmlBuilder.build();
        }

        Integer numericRes = null;
        Product productRes = null;
        switch (command) {
            case "sum":
                numericRes = databaseManager.getPricesSum();
                break;
            case "count":
                numericRes = databaseManager.getProductsCount();
                break;
            case "max":
                productRes = databaseManager.getMaxPriceProduct();
                break;
            case "min":
                productRes = databaseManager.getMinPriceProduct();
                break;
        }

        if (numericRes != null) {
            htmlBuilder.addTextWithNewLine(String.valueOf(numericRes));
        } else if (productRes != null) {
            htmlBuilder.addTextWithNewLine(productRes.name + "\t" + productRes.price);
        } else {
            htmlBuilder.addTextWithNewLine("No products in database");
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
