package servlets;

import com.bittercode.model.Book;
import com.bittercode.model.UserRole;
import com.bittercode.service.BookService;
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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ReceiptServletTest extends TestCase {
    @Mock
    BookService mockBookService;

    @InjectMocks
    ReceiptServlet receiptServlet = new ReceiptServlet();
    @Test
    public void testService(){
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

            receiptServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("CustomerLogin.html");

        } catch(Exception e){
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 2 (FALSE): NOT exceptions thrown
        // Predicate 3 (TRUE) : bQty < quantity
        //
        reset(spyReq,spyRes,mockPW,mockRD);
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(new Book(
                "barcode", "Title", "author", 20, 2
        ));
        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){

            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(true);
            when(mockBookService.getAllBooks()).thenReturn(mockBooks);
            when(spyRes.getWriter()).thenReturn(mockPW);
            when(spyReq.getParameter("qty1")).thenReturn("5");

            receiptServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("CustomerHome.html");
            verify(mockPW).println("</table><div class=\"tab\" style='color:red;'>Please Select the Qty less than Available Books Quantity</div>");


        } catch(Exception e){
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 2 (FALSE): NOT exceptions thrown
        // Predicate 3 (FALSE) : NOT bQty < quantity
        // Predicate 4 (TRUE) : getChecked.equals("pay")
        //

        reset(spyReq,spyRes,mockPW,mockRD);

        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){

            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(true);
            when(mockBookService.getAllBooks()).thenReturn(mockBooks);
            when(spyRes.getWriter()).thenReturn(mockPW);
            when(spyReq.getParameter("qty1")).thenReturn("1");
            when(spyReq.getParameter("checked1")).thenReturn("pay");

            receiptServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("CustomerHome.html");
            verify(mockPW, never()).println("</table><div class=\"tab\" style='color:red;'>Please Select the Qty less than Available Books Quantity</div>");
            verify(mockPW).println("<tr><td>barcode</td>");

        } catch(Exception e){
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 4 (FALSE) : NOT getChecked.equals("pay")
        //

        reset(spyReq,spyRes,mockPW,mockRD);

        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){

            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(true);
            when(mockBookService.getAllBooks()).thenReturn(mockBooks);
            when(spyRes.getWriter()).thenReturn(mockPW);
            when(spyReq.getParameter("qty1")).thenReturn("1");
            when(spyReq.getParameter("checked1")).thenReturn("notpay");

            receiptServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("CustomerHome.html");
            verify(mockPW, never()).println("</table><div class=\"tab\" style='color:red;'>Please Select the Qty less than Available Books Quantity</div>");
            verify(mockPW, never()).println("<tr><td>barcode</td>");

        } catch(Exception e){
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 2 (TRUE) : exceptions thrown
        //

        reset(spyReq,spyRes,mockPW,mockRD);

        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){

            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(true);
            when(mockBookService.getAllBooks()).thenReturn(mockBooks);
            when(spyRes.getWriter()).thenReturn(mockPW);
            when(spyReq.getRequestDispatcher("CustomerHome.html")).thenReturn(mockRD);
            doThrow(new ServletException()).when(mockRD).include(spyReq, spyRes);


            receiptServlet.service(spyReq, spyRes);

            verify(mockPW, never()).println("<div class=\"tab\">Your order status is as below</div>");
            verify(mockPW, never()).println("</table><div class=\"tab\" style='color:red;'>Please Select the Qty less than Available Books Quantity</div>");
            verify(mockPW, never()).println("<tr><td>barcode</td>");

        } catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }

}