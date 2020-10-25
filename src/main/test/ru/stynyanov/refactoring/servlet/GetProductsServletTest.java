package ru.stynyanov.refactoring.servlet;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ru.stynyanov.refactoring.product.Product;
import ru.stynyanov.refactoring.product.ProductsDBManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class GetProductsServletTest {

    @Test
    public void getProductTest() throws Exception {
        Product testProduct1 = new Product("iPhone 12 Pro", 99990);
        Product testProduct2 = new Product("iPhone 12 Pro Max", 109990);
        ProductsDBManager dbManager = mock(ProductsDBManager.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ArgumentCaptor<Integer> responseStatus = ArgumentCaptor.forClass(Integer.class);

        when(dbManager.getAllProducts()).thenReturn(Arrays.asList(testProduct1, testProduct2));

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new GetProductsServlet(dbManager).doGet(request, response);

        verify(response).setStatus(responseStatus.capture());
        writer.flush();
        String expected = String.format(
                "<html><body>\n" +
                "%s\t%d</br>\n" +
                "%s\t%d</br>\n" +
                "</body></html>",
                testProduct1.name, testProduct1.price,
                testProduct2.name, testProduct2.price);
        assertEquals(expected, stringWriter.toString());

        assertEquals((Integer) HttpServletResponse.SC_OK, responseStatus.getValue());
    }
}
