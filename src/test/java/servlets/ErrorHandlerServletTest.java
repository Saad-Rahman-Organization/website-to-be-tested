package servlets;

import com.bittercode.model.StoreException;
import com.bittercode.model.UserRole;
import com.bittercode.util.StoreUtil;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.RequestDispatcher;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ErrorHandlerServletTest extends TestCase {

    ErrorHandlerServlet errorHandlerServlet = new ErrorHandlerServlet();

    @Test
    public void testService(){

        //
        // Predicate 1 (TRUE): StoreUtil.isLoggedIn(UserRole.CUSTOMER, req.getSession())
        // Predicate 2 (FALSE): NOT (NOT StoreUtil.isLoggedIn(UserRole.CUSTOMER, req.getSession()) AND StoreUtil.isLoggedIn(UserRole.SELLER, req.getSession()))
        //
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        MockHttpServletRequest spyReq = spy(req);
        MockHttpServletResponse spyRes = spy(res);


        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(true);

            errorHandlerServlet.service(spyReq,spyRes);

            verify(spyReq).getRequestDispatcher("CustomerHome.html");
            verify(spyReq, never()).getRequestDispatcher("SellerHome.html");
            verify(spyReq, never()).getRequestDispatcher("index.html");

        }catch(Exception e){
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 1 (FALSE): StoreUtil.isLoggedIn(UserRole.CUSTOMER, req.getSession())
        // Predicate 2 (TRUE): (NOT StoreUtil.isLoggedIn(UserRole.CUSTOMER, req.getSession()) AND StoreUtil.isLoggedIn(UserRole.SELLER, req.getSession()))
        //


        reset(spyReq, spyRes);
        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(false);
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);

            errorHandlerServlet.service(spyReq,spyRes);

            verify(spyReq, never()).getRequestDispatcher("CustomerHome.html");
            verify(spyReq).getRequestDispatcher("SellerHome.html");
            verify(spyReq, never()).getRequestDispatcher("index.html");

        }catch(Exception e){
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 1 (FALSE): StoreUtil.isLoggedIn(UserRole.CUSTOMER, req.getSession())
        // Predicate 2 (FALSE): (NOT StoreUtil.isLoggedIn(UserRole.CUSTOMER, req.getSession()) AND StoreUtil.isLoggedIn(UserRole.SELLER, req.getSession()))
        //


        reset(spyReq, spyRes);
        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(false);
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(false);

            errorHandlerServlet.service(spyReq,spyRes);

            verify(spyReq, never()).getRequestDispatcher("CustomerHome.html");
            verify(spyReq, never()).getRequestDispatcher("SellerHome.html");
            verify(spyReq).getRequestDispatcher("index.html");

        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }

}