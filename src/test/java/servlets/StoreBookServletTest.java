package servlets;

import com.bittercode.model.Book;
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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StoreBookServletTest extends TestCase {

    @Mock
    BookService mockBookServ;

    @InjectMocks
    StoreBookServlet storeBookServlet = new StoreBookServlet();

    @Test
    public void testService(){

        //
        // Predicate 1 (TRUE): NOT logged in as seller
        //

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        MockHttpServletRequest spyReq = spy(req);
        MockHttpServletResponse spyRes = spy(res);

        RequestDispatcher mockRD = mock(RequestDispatcher.class);
        PrintWriter mockPW = mock(PrintWriter.class);

        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){

            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(false);

            storeBookServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("SellerLogin.html");

        } catch(Exception e){
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 1 (FALSE): logged in as seller
        // Predicate 2 (FALSE): NOT excpetions thrown
        // Predicate 3 (TRUE) : (books == null || books.size() == 0)
        //

        reset(spyReq,spyRes,mockPW,mockRD);
        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){

            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);
            when(mockBookServ.getAllBooks()).thenReturn(null);
            when(spyRes.getWriter()).thenReturn(mockPW);
            storeBookServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("SellerHome.html");
            verify(mockPW).println("    <tr style='background-color:green'>\r\n"
                    + "      <th scope=\"row\" colspan='6' style='color:yellow; text-align:center;'> No Books Available in the store </th>\r\n"
                    + "    </tr>\r\n");

        } catch(Exception e){
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 3 (TRUE) : (books == null || books.size() == 0)
        //

        reset(spyReq,spyRes,mockPW,mockRD);
        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){

            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);
            when(mockBookServ.getAllBooks()).thenReturn(new ArrayList<>());
            when(spyRes.getWriter()).thenReturn(mockPW);
            storeBookServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("SellerHome.html");
            verify(mockPW).println("    <tr style='background-color:green'>\r\n"
                    + "      <th scope=\"row\" colspan='6' style='color:yellow; text-align:center;'> No Books Available in the store </th>\r\n"
                    + "    </tr>\r\n");

        } catch(Exception e){
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 3 (FALSE) : (books == null || books.size() == 0)
        //

        reset(spyReq,spyRes,mockPW,mockRD);
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(new Book(
                "barcode", "Title", "author", 20, 2
        ));
        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){

            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);
            when(mockBookServ.getAllBooks()).thenReturn(mockBooks);
            when(spyRes.getWriter()).thenReturn(mockPW);
            storeBookServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("SellerHome.html");
            verify(mockPW,never()).println("    <tr style='background-color:green'>\r\n"
                    + "      <th scope=\"row\" colspan='6' style='color:yellow; text-align:center;'> No Books Available in the store </th>\r\n"
                    + "    </tr>\r\n");

        } catch(Exception e){
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 2 (TRUE) : exceptions thrown
        //

        reset(spyReq,spyRes,mockPW,mockRD);

        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){

            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);
            when(spyRes.getWriter()).thenReturn(mockPW);
            when(spyReq.getRequestDispatcher("SellerHome.html")).thenReturn(mockRD);
            doThrow(new ServletException()).when(mockRD).include(spyReq,spyRes);

            storeBookServlet.service(spyReq, spyRes);

            verify(mockPW,never()).println("<div class='container'>");

        } catch(Exception e){
            e.printStackTrace();
            fail();
        }


    }
}