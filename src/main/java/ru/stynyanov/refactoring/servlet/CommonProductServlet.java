package ru.stynyanov.refactoring.servlet;

import ru.stynyanov.refactoring.product.ProductsDBManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class CommonProductServlet extends HttpServlet {

    final ProductsDBManager databaseManager;

    CommonProductServlet(ProductsDBManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(executeRequest(request));
    }

    /**
     * Method that executes a request and returns HTML string with result content
     *
     * @param request request to process
     * @return HTML string with request result
     */
    protected abstract String executeRequest(HttpServletRequest request);
}
