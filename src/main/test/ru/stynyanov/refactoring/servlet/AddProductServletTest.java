package ru.stynyanov.refactoring.servlet;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ru.stynyanov.refactoring.product.Product;
import ru.stynyanov.refactoring.product.ProductsDBManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AddProductServletTest {

    @Test
    public void addProductTest() throws Exception {
        Product testProduct = new Product("iPhone 12 Pro", 99990);
        ProductsDBManager dbManager = mock(ProductsDBManager.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        ArgumentCaptor<Integer> responseStatus = ArgumentCaptor.forClass(Integer.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("name")).thenReturn(testProduct.name);
        when(request.getParameter("price")).thenReturn(String.valueOf(testProduct.price));

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new AddProductServlet(dbManager).doGet(request, response);

        verify(request, atLeast(1)).getParameter("name");
        verify(request, atLeast(1)).getParameter("price");
        verify(response).setStatus(responseStatus.capture());
        writer.flush();

        String expected =
                "<html><body>\n" +
                "OK</br>\n" +
                "</body></html>";
        assertEquals(expected, stringWriter.toString());

        assertEquals((Integer) HttpServletResponse.SC_OK, responseStatus.getValue());
    }
}
