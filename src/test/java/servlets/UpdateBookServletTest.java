package servlets;

import com.bittercode.constant.ResponseCode;
import com.bittercode.constant.db.BooksDBConstants;
import com.bittercode.model.Book;
import com.bittercode.model.UserRole;
import com.bittercode.service.BookService;
import com.bittercode.util.StoreUtil;
import com.mysql.cj.x.protobuf.MysqlxCrud;
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
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UpdateBookServletTest extends TestCase {
    @Mock
    BookService mockBookServ;

    @InjectMocks
    UpdateBookServlet updateBookServlet = new UpdateBookServlet();

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

            updateBookServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("SellerLogin.html");

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 1 (FALSE): logged in as seller
        // Predicate 2 (FALSE): NOT exceptions thrown
        // Predicate 3 (TRUE) : req.getParameter("updateFormSubmitted") != null
        // Predicate 4 (TRUE) : ResponseCode.SUCCESS.name().equalsIgnoreCase(message)
        //
        reset(spyReq, spyRes, mockPW, mockRD, mockBookServ);
        req.addParameter("updateFormSubmitted", "nonNull");
        req.addParameter("bookId", "nonNull");
        req.addParameter(BooksDBConstants.COLUMN_PRICE, "20");
        req.addParameter(BooksDBConstants.COLUMN_QUANTITY, "2");
        try (MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)) {

            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);
            when(mockBookServ.updateBook(any())).thenReturn(ResponseCode.SUCCESS.name());
            when(spyRes.getWriter()).thenReturn(mockPW);
            updateBookServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("SellerHome.html");
            verify(mockBookServ).updateBook(any());
            verify(mockPW).println("<table class=\"tab\"><tr><td>Book Detail Updated Successfully!</td></tr></table>");


        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 4 (FALSE) : ResponseCode.SUCCESS.name().equalsIgnoreCase(message)
        //
        reset(spyReq, spyRes, mockPW, mockRD, mockBookServ);
        req.removeAllParameters();
        req.addParameter("updateFormSubmitted", "nonNull");
        req.addParameter("bookId", "nonNull");
        req.addParameter(BooksDBConstants.COLUMN_PRICE, "20");
        req.addParameter(BooksDBConstants.COLUMN_QUANTITY, "2");
        try (MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)) {

            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);
            when(mockBookServ.updateBook(any())).thenReturn(ResponseCode.FAILURE.name());
            when(spyRes.getWriter()).thenReturn(mockPW);
            updateBookServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("SellerHome.html");
            verify(mockBookServ).updateBook(any());
            verify(mockPW).println("<table class=\"tab\"><tr><td>Failed to Update Book!!</td></tr></table>");


        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 3 (FALSE) : NOT req.getParameter("updateFormSubmitted") != null
        // Predicate 5 (TRUE) : bookId != null
        //
        reset(spyReq, spyRes, mockPW, mockRD, mockBookServ);
        req.removeAllParameters();
        req.addParameter("updateFormSubmitted", (String) null);
        req.addParameter("bookId", "nonNull");
        req.addParameter(BooksDBConstants.COLUMN_PRICE, "20");
        req.addParameter(BooksDBConstants.COLUMN_QUANTITY, "2");
        try (MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)) {

            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);
            when(mockBookServ.getBookById(any())).thenReturn(new Book());
            when(spyRes.getWriter()).thenReturn(mockPW);
            updateBookServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("SellerHome.html");
            verify(spyReq).getParameter("bookId");
            verify(mockBookServ, never()).updateBook(any());
            verify(mockBookServ).getBookById(any());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 5 (FALSE) : bookId == null
        //

        reset(spyReq, spyRes, mockPW, mockRD, mockBookServ);
        req.removeAllParameters();
        req.addParameter("updateFormSubmitted", (String) null);
        req.addParameter("bookId", (String) null);
        req.addParameter(BooksDBConstants.COLUMN_PRICE, "20");
        req.addParameter(BooksDBConstants.COLUMN_QUANTITY, "2");
        try (MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)) {

            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);
            when(spyRes.getWriter()).thenReturn(mockPW);
            updateBookServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("SellerHome.html");
            verify(spyReq).getParameter("bookId");
            verify(mockBookServ, never()).updateBook(any());
            verify(mockBookServ, never()).getBookById(any());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 2 (TRUE) : Exceptions thrown
        //

        reset(spyReq, spyRes, mockPW, mockRD, mockBookServ);
        req.removeAllParameters();
        req.addParameter("updateFormSubmitted", "notNull");
        req.addParameter("bookId", (String) null);
        req.addParameter(BooksDBConstants.COLUMN_PRICE, "NaN");
        req.addParameter(BooksDBConstants.COLUMN_QUANTITY, "NaN");
        try (MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)) {

            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);
            when(spyRes.getWriter()).thenReturn(mockPW);
            updateBookServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("SellerHome.html");

            verify(mockBookServ, never()).updateBook(any());
            verify(mockBookServ, never()).getBookById(any());

            verify(mockPW).println("<table class=\"tab\"><tr><td>Failed to Load Book data!!</td></tr></table>");

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }


    }

}