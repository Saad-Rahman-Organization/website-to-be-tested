package servlets;

import com.bittercode.model.UserRole;
import com.bittercode.service.BookService;
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
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import java.io.PrintWriter;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProcessPaymentServletTest extends TestCase {
    @Mock
    BookService mockBookService;

    @InjectMocks
    ProcessPaymentServlet processPaymentServlet = new ProcessPaymentServlet();

    @Test
    public void testService() {
        //
        // Predicate 1 (TRUE): NOT logged in as customer
        //
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        MockHttpServletRequest spyReq = spy(req);
        MockHttpServletResponse spyRes = spy(res);

        RequestDispatcher mockRD = mock(RequestDispatcher.class);
        PrintWriter mockPW = mock(PrintWriter.class);

        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){

            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(false);

            processPaymentServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("CustomerLogin.html");

        } catch(Exception e){
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 1 (FALSE):  logged in as customer
        // Predicate 2 (FALSE): NOT exceptions thrown
        // Predicate 3 (TRUE): session.getAttribute("cartItems") != null
        //

        reset(spyReq,spyRes,mockPW,mockRD);
        MockHttpSession mockSes = new MockHttpSession();
        mockSes.setAttribute("cartItems", new ArrayList<>());
        MockHttpSession spySes = spy(mockSes);
        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            when(spyReq.getSession()).thenReturn(spySes);
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(true);

            processPaymentServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("CustomerHome.html");
            verify(spySes, atLeast(2)).getAttribute("cartItems");

        } catch(Exception e){
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 3 (False): session.getAttribute("cartItems") != null
        //

        reset(spyReq,spyRes,mockPW,mockRD, spySes);
        mockSes.setAttribute("cartItems", null);
        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            when(spyReq.getSession()).thenReturn(spySes);
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(true);

            processPaymentServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("CustomerHome.html");
            verify(spySes, atMostOnce()).getAttribute("cartItems");

        } catch(Exception e){
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 2 (FALSE): NOT exceptions thrown
        //

        reset(spyReq,spyRes,mockPW,mockRD, spySes);
        mockSes.setAttribute("cartItems", null);
        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            when(spyReq.getRequestDispatcher("CustomerHome.html")).thenReturn(mockRD);
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(true);

            doThrow(new ServletException()).when(mockRD).include(spyReq,spyRes);

            processPaymentServlet.service(spyReq, spyRes);

            verify(spySes, never()).getAttribute("cartItems");

        } catch(Exception e){
            e.printStackTrace();
            fail();
        }

    }
}