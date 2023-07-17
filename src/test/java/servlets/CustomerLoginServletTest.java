package servlets;

import com.bittercode.model.User;
import com.bittercode.model.UserRole;
import com.bittercode.service.UserService;
import com.bittercode.util.StoreUtil;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerLoginServletTest extends TestCase {
    @Mock
    UserService mockUserService;

    @InjectMocks
    CustomerLoginServlet customerLoginServlet = new CustomerLoginServlet();

    @Test
    public void testDoPost(){
        //
        // Predicate 1 (FALSE): NOT exceptions thrown
        // Predicate 2 (TRUE): user != null
        //

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        MockHttpServletRequest spyReq = spy(req);
        MockHttpServletResponse spyRes = spy(res);

        RequestDispatcher mockRD = mock(RequestDispatcher.class);
        PrintWriter mockPW = mock(PrintWriter.class);

        try{
            when(mockUserService.login(any(),any(),any(),any())).thenReturn(new User());
            customerLoginServlet.doPost(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("CustomerHome.html");

        }catch(Exception e){
            System.out.println(e);
            fail();
        }

        //
        // Predicate 2 (FALSE): user == null
        //

        reset(spyRes, spyReq, mockPW, mockRD);
        try{
            when(mockUserService.login(any(),any(),any(),any())).thenReturn(null);
            customerLoginServlet.doPost(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("CustomerLogin.html");

        }catch(Exception e){
            System.out.println(e);
            fail();
        }

        //
        // Predicate 1 (TRUE) : exception(s) thrown
        //

        reset(spyRes, spyReq, mockPW, mockRD);
        try{
            when(mockUserService.login(any(),any(),any(),any())).thenReturn(new User());
            when(spyReq.getRequestDispatcher("CustomerHome.html")).thenReturn(mockRD);
            when(spyRes.getWriter()).thenReturn(mockPW);
            doThrow(new ServletException()).when(mockRD).include(spyReq, spyRes);
            customerLoginServlet.doPost(spyReq, spyRes);

            verify(mockPW,never()).println(anyString());
        }catch(Exception e){
            System.out.println(e);
            fail();
        }
    }

}