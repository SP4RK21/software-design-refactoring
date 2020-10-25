package ru.stynyanov.refactoring.servlet;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ru.stynyanov.refactoring.product.Product;
import ru.stynyanov.refactoring.product.ProductsDBManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class QueryServletTest {

    @Test
    public void testMaxQuery() throws IOException {
        Product testProduct = new Product("iPhone 12 Pro Max", 109990);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ProductsDBManager dbManager = mock(ProductsDBManager.class);
        ArgumentCaptor<Integer> responseStatus = ArgumentCaptor.forClass(Integer.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(request.getParameter("command")).thenReturn("max");
        when(response.getWriter()).thenReturn(writer);

        when(dbManager.getMaxPriceProduct()).thenReturn(testProduct);

        new QueryServlet(dbManager).doGet(request, response);

        verify(response).setStatus(responseStatus.capture());
        writer.flush();
        String expected = String.format(
                "<html><body>\n" +
                "<h1>Product with max price:</h1>\n" +
                "%s\t%d</br>\n" +
                "</body></html>",
                testProduct.name, testProduct.price);
        assertEquals(expected, stringWriter.toString());

        assertEquals((Integer) HttpServletResponse.SC_OK, responseStatus.getValue());
    }

    @Test
    public void testMaxQueryWithEmptyDB() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ProductsDBManager dbManager = mock(ProductsDBManager.class);
        ArgumentCaptor<Integer> responseStatus = ArgumentCaptor.forClass(Integer.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(request.getParameter("command")).thenReturn("max");
        when(response.getWriter()).thenReturn(writer);

        when(dbManager.getMaxPriceProduct()).thenReturn(null);

        new QueryServlet(dbManager).doGet(request, response);

        verify(response).setStatus(responseStatus.capture());
        writer.flush();
        String expected =
                "<html><body>\n" +
                "<h1>Product with max price:</h1>\n" +
                "No products in database</br>\n" +
                "</body></html>";
        assertEquals(expected, stringWriter.toString());

        assertEquals((Integer) HttpServletResponse.SC_OK, responseStatus.getValue());
    }

    @Test
    public void testMinQuery() throws IOException {
        Product testProduct = new Product("iPhone SE", 39990);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ProductsDBManager dbManager = mock(ProductsDBManager.class);
        ArgumentCaptor<Integer> responseStatus = ArgumentCaptor.forClass(Integer.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(request.getParameter("command")).thenReturn("min");
        when(response.getWriter()).thenReturn(writer);

        when(dbManager.getMinPriceProduct()).thenReturn(testProduct);

        new QueryServlet(dbManager).doGet(request, response);

        verify(response).setStatus(responseStatus.capture());
        writer.flush();
        String expected = String.format(
                "<html><body>\n" +
                "<h1>Product with min price:</h1>\n" +
                "%s\t%d</br>\n" +
                "</body></html>",
                testProduct.name, testProduct.price);
        assertEquals(expected, stringWriter.toString());

        assertEquals((Integer) HttpServletResponse.SC_OK, responseStatus.getValue());
    }

    @Test
    public void testSumQuery() throws IOException {
        int testSum = 249970;
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ProductsDBManager dbManager = mock(ProductsDBManager.class);
        ArgumentCaptor<Integer> responseStatus = ArgumentCaptor.forClass(Integer.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(request.getParameter("command")).thenReturn("sum");
        when(response.getWriter()).thenReturn(writer);

        when(dbManager.getPricesSum()).thenReturn(testSum);

        new QueryServlet(dbManager).doGet(request, response);

        verify(response).setStatus(responseStatus.capture());
        writer.flush();
        String expected = String.format(
                "<html><body>\n" +
                "<h1>Summary price:</h1>\n" +
                "%d</br>\n" +
                "</body></html>",
                testSum);
        assertEquals(expected, stringWriter.toString());

        assertEquals((Integer) HttpServletResponse.SC_OK, responseStatus.getValue());
    }

    @Test
    public void testCountQuery() throws IOException {
        int testCount = 3;
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ProductsDBManager dbManager = mock(ProductsDBManager.class);
        ArgumentCaptor<Integer> responseStatus = ArgumentCaptor.forClass(Integer.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(request.getParameter("command")).thenReturn("count");
        when(response.getWriter()).thenReturn(writer);

        when(dbManager.getProductsCount()).thenReturn(testCount);

        new QueryServlet(dbManager).doGet(request, response);

        verify(response).setStatus(responseStatus.capture());
        writer.flush();
        String expected = String.format(
                "<html><body>\n" +
                "<h1>Number of products:</h1>\n" +
                "%d</br>\n" +
                "</body></html>",
                testCount);
        assertEquals(expected, stringWriter.toString());

        assertEquals((Integer) HttpServletResponse.SC_OK, responseStatus.getValue());
    }

    @Test
    public void testUnknownQuery() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ArgumentCaptor<Integer> responseStatus = ArgumentCaptor.forClass(Integer.class);
        ProductsDBManager dbManager = mock(ProductsDBManager.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(request.getParameter("command")).thenReturn("unknown");
        when(response.getWriter()).thenReturn(writer);

        new QueryServlet(dbManager).doGet(request, response);

        verify(response).setStatus(responseStatus.capture());
        writer.flush();
        String expected =
                "<html><body>\n" +
                "<h1>Unknown command: unknown</h1>\n" +
                "</body></html>";
        assertEquals(expected, stringWriter.toString());

        assertEquals((Integer) HttpServletResponse.SC_OK, responseStatus.getValue());
    }
}
