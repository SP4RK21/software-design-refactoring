package ru.stynyanov.refactoring.servlet;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ru.stynyanov.refactoring.database.DatabaseManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class AddProductServletTest {

    @Test
    public void addProductTest() throws Exception {
        DatabaseManager dbManager = mock(DatabaseManager.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        ArgumentCaptor<Integer> responseStatus = ArgumentCaptor.forClass(Integer.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("name")).thenReturn("iPhone 12 Pro");
        when(request.getParameter("price")).thenReturn("99990");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new AddProductServlet(dbManager).doGet(request, response);

        verify(request, atLeast(1)).getParameter("name");
        verify(request, atLeast(1)).getParameter("price");
        verify(response).setStatus(responseStatus.capture());
        writer.flush();
        assertTrue(stringWriter.toString().contains("OK"));

        assertEquals((Integer) HttpServletResponse.SC_OK, responseStatus.getValue());
    }
}
