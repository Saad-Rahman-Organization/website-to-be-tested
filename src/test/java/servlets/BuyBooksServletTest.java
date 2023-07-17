package servlets;

import com.bittercode.model.Book;
import com.bittercode.model.UserRole;
import com.bittercode.service.impl.BookServiceImpl;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BuyBooksServletTest extends TestCase {
    @Mock
    BookServiceImpl bookServMock;

    @InjectMocks
    BuyBooksServlet buyBooksMock = new BuyBooksServlet();

    MockHttpServletRequest req, spyReq;
    MockHttpServletResponse res;

    @Test
    public void testDoPost(){
        //
        // Test 1
        // 2 Predicates, 2 clauses
        // Predicate 1 (TRUE): NOT logged in as customer
        // Predicate 2: not tested
        //
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();

        spyReq = spy(req);

        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(false);

            buyBooksMock.doPost(spyReq,res);

            verify(spyReq).getRequestDispatcher("CustomerLogin.html");
        }catch(Exception e){
            System.out.println(e);
            fail();
        }

        //
        // Predicate 1 (FALSE): NOT NOT logged in as customer
        // Predicate 2 (TRUE): Error occurs
        //
        RequestDispatcher mockRD = mock(RequestDispatcher.class);
        spyReq = spy(req);
        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(true);
            when(spyReq.getRequestDispatcher("CustomerHome.html")).thenReturn(mockRD);
            doThrow(new ServletException()).when(mockRD).include(any(),any());

            buyBooksMock.doPost(spyReq,res);

            verify(spyReq).getRequestDispatcher("CustomerHome.html");
            storeMock.verify(() -> StoreUtil.setActiveTab(any(),any()), never());
        }catch(Exception e){
            System.out.println(e);
            fail();
        }

        //
        // Predicate 1 (FALSE): NOT NOT logged in as customer
        // Predicate 2 (FALSE): Error does not occur
        //

        spyReq = spy(req);
        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(true);
            when(bookServMock.getAllBooks()).thenReturn(new ArrayList<>());
            buyBooksMock.doPost(spyReq,res);

            verify(spyReq).getRequestDispatcher("CustomerHome.html");
            storeMock.verify(() -> StoreUtil.setActiveTab(any(),any()));
        }catch(Exception e){
            System.out.println(e);
            fail();
        }

    }

}