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
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartServletTest extends TestCase {
    @Mock
    BookServiceImpl bookServMock = mock(BookServiceImpl.class);

    @InjectMocks
    CartServlet cartServlet = new CartServlet();

    MockHttpServletRequest req, spyReq;
    MockHttpServletResponse res;

    @Test
    public void testService() {
        //
        // Predicate 1 (TRUE): NOT logged in as customer
        //

        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();

        spyReq = spy(req);

        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(false);

            cartServlet.service(spyReq, res);

            verify(spyReq).getRequestDispatcher("CustomerLogin.html");
        }catch(Exception e){
            System.out.println(e);
            fail();
        }



        //
        // Predicate 1 (FALSE): logged in as customer
        // Predicate 2 (FALSE): NOT exceptions thrown
        // Predicate 3 (TRUE): session.getAttribute("items") != null
        // Predicate 4 (TRUE): (books == null || books.size() == 0)
        // Predicate 5 (FALSE): amount = 0

        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();
        MockHttpServletResponse spyRes = spy(res);
        MockHttpSession mockSes = new MockHttpSession();
        PrintWriter mockPW = mock(PrintWriter.class);
        mockSes.setAttribute("items", "0");
        MockHttpSession spySes = spy(mockSes);
        spyReq = spy(req);

        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            when(spyReq.getSession()).thenReturn(spySes);
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(true);
            when(bookServMock.getBooksByCommaSeperatedBookIds(any())).thenReturn(new ArrayList<>());

            when(spyRes.getWriter()).thenReturn(mockPW);


            cartServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("CustomerHome.html");
            verify(spySes, atLeast(2)).getAttribute("items");
            verify(mockPW).println("    <tr style='background-color:green'>\r\n"
                    + "      <th scope=\"row\" colspan='6' style='color:yellow; text-align:center;'> No Items In the Cart </th>\r\n"
                    + "    </tr>\r\n");
            verify(mockPW, never()).println("    <tr style='background-color:green'>\r\n"
                    + "      <th scope=\"row\" colspan='5' style='color:yellow; text-align:center;'> Total Amount To Pay </th>\r\n"
                    + "      <td colspan='1' style='color:white; font-weight:bold'><span>&#8377;</span> "
                    + 0
                    + "</td>\r\n"
                    + "    </tr>\r\n");
        }catch(Exception e){
            System.out.println(e);
            fail();
        }

        // Predicate 3 (FALSE): session.getAttribute("items") == null
        // Predicate 4 (FALSE): NOT (books == null || books.size() == 0)
        // Predicate 5 (TRUE): amount > 0


        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();
        spyRes = spy(res);
        mockSes = new MockHttpSession();
        mockPW = mock(PrintWriter.class);
        mockSes.setAttribute("items", "0");
        mockSes.setAttribute("qty_barcode", 2);
        reset(spySes);
        spyReq = spy(req);

        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(new Book(
                "barcode", "Title", "author", 20, 2
        ));

        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            when(spyReq.getSession()).thenReturn(spySes);
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(true);
            when(bookServMock.getBooksByCommaSeperatedBookIds(any())).thenReturn(mockBooks);

            when(spyRes.getWriter()).thenReturn(mockPW);


            cartServlet.service(spyReq, spyRes);

            verify(mockPW, never()).println("    <tr style='background-color:green'>\r\n"
                    + "      <th scope=\"row\" colspan='6' style='color:yellow; text-align:center;'> No Items In the Cart </th>\r\n"
                    + "    </tr>\r\n");
            verify(mockPW, never()).println("    <tr style='background-color:green'>\r\n"
                    + "      <th scope=\"row\" colspan='5' style='color:yellow; text-align:center;'> Total Amount To Pay </th>\r\n"
                    + "      <td colspan='1' style='color:white; font-weight:bold'><span>&#8377;</span> "
                    + 40.0
                    + "</td>\r\n"
                    + "    </tr>\r\n");
        }catch(Exception e){
            System.out.println(e);
            fail();
        }





    }

}