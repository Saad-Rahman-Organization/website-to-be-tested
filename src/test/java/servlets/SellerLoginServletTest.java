package servlets;

import com.bittercode.model.User;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SellerLoginServletTest extends TestCase {

    @Mock
    UserService mockUserServ;

    @InjectMocks
    SellerLoginServlet sellerLoginServlet = new SellerLoginServlet();

    @Test
    public void testDoPost(){
        //
        // Predicate 1 (FALSE): NOT (exceptions thrown)
        // Predicate 2 (TRUE): user != null
        //
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        MockHttpServletRequest spyReq = spy(req);
        MockHttpServletResponse spyRes = spy(res);

        RequestDispatcher mockRD = mock(RequestDispatcher.class);
        PrintWriter mockPW = mock(PrintWriter.class);

        try {
            when(mockUserServ.login(any(),any(),any(),any())).thenReturn(new User());

            sellerLoginServlet.doPost(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("SellerHome.html");

        }catch(Exception e){
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 2 (FALSE): user == null
        //

        reset(spyReq, spyRes, mockPW, mockRD, mockUserServ);

        try {
            when(mockUserServ.login(any(),any(),any(),any())).thenReturn(null);

            sellerLoginServlet.doPost(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("SellerLogin.html");

        }catch(Exception e){
            e.printStackTrace();
            fail();
        }

        //
        //Predicate 1 (TRUE): exceptions thrown
        //

        reset(spyReq, spyRes, mockPW, mockRD, mockUserServ);

        try {
            when(mockUserServ.login(any(),any(),any(),any())).thenReturn(new User());
            when(spyReq.getRequestDispatcher("SellerHome.html")).thenReturn(mockRD);
            doThrow(new ServletException()).when(mockRD).include(spyReq,spyRes);
            sellerLoginServlet.doPost(spyReq, spyRes);

            verify(mockPW,never()).println(anyString());



        }catch(Exception e){
            e.printStackTrace();
            fail();
        }



    }

}