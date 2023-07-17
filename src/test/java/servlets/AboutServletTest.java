package servlets;

import junit.framework.TestCase;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.bittercode.model.UserRole;
import servlets.AboutServlet;
import com.bittercode.util.StoreUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class AboutServletTest extends TestCase {
    
    AboutServlet about;
    MockHttpServletRequest req, spyReq;
    MockHttpServletResponse res;

    @Test
    public void testService() {
        //
        // Scenario: logged in as customer
        // 1 Predicate, 1 clause
        //


        // PREDICATE is TRUE
        // Set up mocks
        about = new AboutServlet();
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();

        spyReq = spy(req);


        try(MockedStatic<StoreUtil>  storeMock = Mockito.mockStatic(StoreUtil.class)){
            // Predicate = true
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(true);


            //run
            about.service(spyReq, res);

            //make sure the correct pathway was chosen
            verify(spyReq).getRequestDispatcher("CustomerHome.html");
        } catch(Exception e){
            System.out.println(e);
            fail();
        }

        // PREDICATE is False
        // Set up mocks
        about = new AboutServlet();
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();

        spyReq = spy(req);


        try(MockedStatic<StoreUtil>  storeMock = Mockito.mockStatic(StoreUtil.class)){
            // Predicate = false
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(false);


            //run
            about.service(spyReq, res);

            //make sure the correct pathway was chosen
            verify(spyReq).getRequestDispatcher("login.html");
        } catch(Exception e){
            System.out.println(e);
            fail();
        }

        //
        // Scenario: logged in as seller
        // 1 Predicate, 2 clauses
        // 2 tests:
        // NOT Logged in as customer AND logged in as seller
        // NOT Logged in as customer AND NOT logged in as seller
        //


        // PREDICATE is TRUE
        // NOT Logged in as customer AND logged in as seller
        // Set up mocks
        about = new AboutServlet();
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();

        spyReq = spy(req);


        try(MockedStatic<StoreUtil>  storeMock = Mockito.mockStatic(StoreUtil.class)){
            // Predicate = true
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(false);
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);

            //run
            about.service(spyReq, res);

            //make sure the correct pathway was chosen
            verify(spyReq).getRequestDispatcher("SellerHome.html");
        } catch(Exception e){
            System.out.println(e);
            fail();
        }

        // PREDICATE is False
        // Set up mocks
        about = new AboutServlet();
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();

        spyReq = spy(req);


        try(MockedStatic<StoreUtil>  storeMock = Mockito.mockStatic(StoreUtil.class)){
            // Predicate = false
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(false);
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(false);


            //run
            about.service(spyReq, res);

            //make sure the correct pathway was chosen
            verify(spyReq).getRequestDispatcher("login.html");
        } catch(Exception e){
            System.out.println(e);
            fail();
        }


        

    }
}