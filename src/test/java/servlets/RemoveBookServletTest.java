package servlets;

import com.bittercode.constant.ResponseCode;
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

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RemoveBookServletTest extends TestCase {
    @Mock
    BookService mockBookService;

    @InjectMocks
    RemoveBookServlet removeBookServlet = new RemoveBookServlet();
    @Test
    public void testService() {
        //
        // Predicate 1 (TRUE): NOT logged in as seller
        //
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        MockHttpServletRequest spyReq = spy(req);
        MockHttpServletResponse spyRes = spy(res);

        RequestDispatcher mockRD = mock(RequestDispatcher.class);
        PrintWriter mockPW = mock(PrintWriter.class);

        try (MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)) {

            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(false);

            removeBookServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("SellerLogin.html");

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 1 (FALSE): logged in as seller
        // Predicate 2 (FALSE): NOT exceptions thrown
        // Predicate 3 (TRUE) : bookId == null || bookId.isBlank()
        //
        reset(spyReq, spyRes, mockPW, mockRD);
        req.addParameter("bookId", (String) null);
        try (MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)) {
            when(spyRes.getWriter()).thenReturn(mockPW);
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);

            removeBookServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("SellerHome.html");
            String form = "<form action=\"removebook\" method=\"post\" class='my-5'>\r\n"
                    + "        <table class=\"tab\">\r\n"
                    + "        <tr>\r\n"
                    + "            <td>\r\n"
                    + "                <label for=\"bookCode\">Enter BookId to Remove </label>\r\n"
                    + "                <input type=\"text\" name=\"bookId\" placeholder=\"Enter Book Id\" id=\"bookCode\" required>\r\n"
                    + "                <input class=\"btn btn-danger my-2\" type=\"submit\" value=\"Remove Book\">\r\n"
                    + "            </td>\r\n"
                    + "        </tr>\r\n"
                    + "\r\n"
                    + "        </table>\r\n"
                    + "    </form>";
            verify(mockPW).println(form);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 3 (TRUE) : bookId == null || bookId.isBlank()
        //
        reset(spyReq, spyRes, mockPW, mockRD);
        req.removeParameter("bookId");
        req.addParameter("bookId", " ");
        try (MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)) {
            when(spyRes.getWriter()).thenReturn(mockPW);
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);

            removeBookServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("SellerHome.html");
            String form = "<form action=\"removebook\" method=\"post\" class='my-5'>\r\n"
                    + "        <table class=\"tab\">\r\n"
                    + "        <tr>\r\n"
                    + "            <td>\r\n"
                    + "                <label for=\"bookCode\">Enter BookId to Remove </label>\r\n"
                    + "                <input type=\"text\" name=\"bookId\" placeholder=\"Enter Book Id\" id=\"bookCode\" required>\r\n"
                    + "                <input class=\"btn btn-danger my-2\" type=\"submit\" value=\"Remove Book\">\r\n"
                    + "            </td>\r\n"
                    + "        </tr>\r\n"
                    + "\r\n"
                    + "        </table>\r\n"
                    + "    </form>";
            verify(mockPW).println(form);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 3 (FALSE): NOT (bookId == null || bookId.isBlank())
        // Predicate 4 (TRUE): ResponseCode.SUCCESS.name().equalsIgnoreCase(responseCode)
        //
        reset(spyReq, spyRes, mockPW, mockRD);
        req.removeParameter("bookId");
        req.addParameter("bookId", "bookID");
        try (MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)) {
            when(spyRes.getWriter()).thenReturn(mockPW);
            when(mockBookService.deleteBookById("bookID")).thenReturn(ResponseCode.SUCCESS.name());
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);

            removeBookServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("SellerHome.html");
            verify(mockPW).println("<table class=\"tab my-5\"><tr><td>Book Removed Successfully</td></tr></table>");

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 4 (FALSE): ResponseCode.SUCCESS.name().equalsIgnoreCase(responseCode)
        //
        reset(spyReq, spyRes, mockPW, mockRD);
        req.removeParameter("bookId");
        req.addParameter("bookId", "bookID");
        try (MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)) {
            when(spyRes.getWriter()).thenReturn(mockPW);
            when(mockBookService.deleteBookById("bookID")).thenReturn(ResponseCode.FAILURE.name());
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);

            removeBookServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("SellerHome.html");
            verify(mockPW).println("<table class=\"tab my-5\"><tr><td>Book Not Available In The Store</td></tr></table>");

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 2 (TRUE): exceptions thrown
        //
        reset(spyReq, spyRes, mockPW, mockRD);
        req.removeParameter("bookId");
        try (MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)) {
            when(spyRes.getWriter()).thenReturn(mockPW);
            when(spyReq.getRequestDispatcher("SellerHome.html")).thenReturn(mockRD);
            doThrow(new ServletException()).when(mockRD).include(spyReq,spyRes);
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);

            removeBookServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("SellerHome.html");
            verify(mockPW, never()).println("<div class='container'>");
            verify(mockPW).println("<table class=\"tab\"><tr><td>Failed to Remove Books! Try Again</td></tr></table>");

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}