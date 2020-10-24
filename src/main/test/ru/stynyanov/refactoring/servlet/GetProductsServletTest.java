package ru.stynyanov.refactoring.servlet;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class GetProductsServletTest {

    @Test
    public void getProductTest() throws Exception {
        HttpServletRequest addRequest = mock(HttpServletRequest.class);
        HttpServletRequest getRequest = mock(HttpServletRequest.class);
        HttpServletResponse addResponse = mock(HttpServletResponse.class);
        HttpServletResponse getResponse = mock(HttpServletResponse.class);
        ArgumentCaptor<Integer> responseStatus = ArgumentCaptor.forClass(Integer.class);

        final String productName = "iPhone 12 Pro";
        final String productPrice = "99990";
        when(addRequest.getParameter("name")).thenReturn(productName);
        when(addRequest.getParameter("price")).thenReturn(productPrice);

        StringWriter addStringWriter = new StringWriter();
        PrintWriter addRequestWriter = new PrintWriter(addStringWriter);
        when(addResponse.getWriter()).thenReturn(addRequestWriter);

        StringWriter getStringWriter = new StringWriter();
        PrintWriter getRequestWriter = new PrintWriter(getStringWriter);
        when(getResponse.getWriter()).thenReturn(getRequestWriter);

        new AddProductServlet().doGet(addRequest, addResponse);
        new GetProductsServlet().doGet(getRequest, getResponse);

        verify(getResponse).setStatus(responseStatus.capture());
        addRequestWriter.flush();
        assertTrue(getStringWriter.toString().startsWith("<html><body>"));
        assertTrue(getStringWriter.toString().endsWith("</body></html>\n"));
        assertTrue(getStringWriter.toString().contains(productName + "\t" + productPrice));

        assertEquals((Integer) HttpServletResponse.SC_OK, responseStatus.getValue());
    }
}
