package servlets;

import com.bittercode.service.UserService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LogoutServletTest extends TestCase {

    @Mock
    UserService mockUserServ;

    @InjectMocks
    LogoutServlet logoutServlet;

    @Test
    public void testDoGet(){
        //
        // Predicate 1 (FALSE): NOT exceptions thrown
        // Predicate 2 (TRUE): logout
        //
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        MockHttpServletRequest spyReq = spy(req);
        MockHttpServletResponse spyRes = spy(res);

        RequestDispatcher mockRD = mock(RequestDispatcher.class);
        PrintWriter mockPW = mock(PrintWriter.class);

        try{
            when(mockUserServ.logout(any())).thenReturn(true);
            when(spyRes.getWriter()).thenReturn(mockPW);

            logoutServlet.doGet(spyReq, spyRes);

            verify(mockPW).println("<table class=\"tab\"><tr><td>Successfully logged out!</td></tr></table>");

        }catch(Exception e){
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 1 (FALSE): NOT exceptions thrown
        // Predicate 2 (FALSE): NOT logout
        //
        reset(spyReq,spyRes,mockPW,mockRD);
        try{
            when(mockUserServ.logout(any())).thenReturn(false);
            when(spyRes.getWriter()).thenReturn(mockPW);

            logoutServlet.doGet(spyReq, spyRes);

            verify(mockPW, never()).println("<table class=\"tab\"><tr><td>Successfully logged out!</td></tr></table>");

        }catch(Exception e){
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 1 (TRUE): exceptions thrown
        //
        reset(spyReq,spyRes,mockPW,mockRD);
        try{
            when(mockUserServ.logout(any())).thenReturn(true);
            when(spyRes.getWriter()).thenReturn(mockPW);
            when(spyReq.getRequestDispatcher("CustomerLogin.html")).thenReturn(mockRD);
            doThrow(new ServletException()).when(mockRD).include(spyReq,spyRes);
            logoutServlet.doGet(spyReq, spyRes);

            verify(mockPW, never()).println("<table class=\"tab\"><tr><td>Successfully logged out!</td></tr></table>");

        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }
}