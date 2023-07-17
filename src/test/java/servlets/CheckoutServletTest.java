package servlets;

import com.bittercode.model.UserRole;
import com.bittercode.util.StoreUtil;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class CheckoutServletTest extends TestCase {
    CheckoutServlet checkoutServlet = new CheckoutServlet();

    @Test
    public void testDoPost(){
        //
        //Predicate 1 (TRUE) : NOT logged in as Customer
        //
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        MockHttpServletRequest spyReq = spy(req);
        MockHttpServletResponse spyRes = spy(res);

        try(MockedStatic<StoreUtil> storeUtil = mockStatic(StoreUtil.class)){
            storeUtil.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(false);

            checkoutServlet.doPost(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("CustomerLogin.html");
        }catch(Exception e){
            System.out.println(e);
            fail();
        }

        //
        //Predicate 1 (FALSE) : logged in as customer
        //Predicate 2 (TRUE) : Exception thrown
        //

        reset(spyReq, spyRes);
        RequestDispatcher mockRD = mock(RequestDispatcher.class);
        PrintWriter mockPW = mock(PrintWriter.class);

        try(MockedStatic<StoreUtil> storeUtil = mockStatic(StoreUtil.class)){
            storeUtil.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(true);
            when(spyReq.getRequestDispatcher("payment.html")).thenReturn(mockRD);
            when(spyRes.getWriter()).thenReturn(mockPW);
            doThrow(new ServletException()).when(mockRD).include(spyReq, spyRes);

            checkoutServlet.doPost(spyReq, spyRes);

            verify(mockPW, never()).println("<input type=\"submit\" value=\"Pay & Place Order\" class=\"btn\">"
                    + "</form>");

        }catch(Exception e){
            System.out.println(e);
            fail();
        }

        //
        //Predicate 2 (FALSE): no exception thrown
        //

        reset(spyReq, spyRes, mockPW, mockRD);

        try(MockedStatic<StoreUtil> storeUtil = mockStatic(StoreUtil.class)){
            storeUtil.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(true);
            when(spyRes.getWriter()).thenReturn(mockPW);

            checkoutServlet.doPost(spyReq, spyRes);

            verify(mockPW).println("<input type=\"submit\" value=\"Pay & Place Order\" class=\"btn\">"
                    + "</form>");

        }catch(Exception e){
            System.out.println(e);
            fail();
        }

    }

}