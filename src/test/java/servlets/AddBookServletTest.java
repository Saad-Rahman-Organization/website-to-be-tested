package servlets;

import com.bittercode.constant.db.BooksDBConstants;
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

import java.io.PrintWriter;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddBookServletTest extends TestCase {
    @Mock
    BookServiceImpl bookServMock;

    @InjectMocks
    AddBookServlet addBook = new AddBookServlet();;
    MockHttpServletRequest req, spyReq;
    MockHttpServletResponse res, spyRes;

    PrintWriter mockPW;
    @Test
    public void testService(){
        //Setup mocks

        //
        // Scenario: NOT logged in as seller
        // 1 predicate, 1 clause
        //

        //
        // Predicate is TRUE
        //
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();

        spyReq = spy(req);

        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(false);

            //run
            addBook.service(spyReq, res);

            //verify
            verify(spyReq).getRequestDispatcher("SellerLogin.html");
        } catch(Exception e){
            System.out.println(e);
            fail();
        }

        //
        // Predicate is FALSE
        //
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();

        spyReq = spy(req);

        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);

            //run
            addBook.service(spyReq, res);

            //verify
            verify(spyReq, never()).getRequestDispatcher("SellerLogin.html");
        } catch(Exception e){
            System.out.println(e);
            fail();
        }

        //
        // Scenario: bname == null OR bName.isBlank
        // 1 predicate, 2 clauses
        //

        //
        // TEST CLAUSE 1: bname == null
        // Since bname is null, isBlank cannot be invoked and therefore guarantees that this is an active clause.
        // CLAUSE IS TRUE
        //

        req = new MockHttpServletRequest();
        req.addParameter(BooksDBConstants.COLUMN_NAME, (String) null); //ensure bName is null

        res = new MockHttpServletResponse();

        mockPW = mock(PrintWriter.class);

        spyReq = spy(req);
        spyRes = spy(res);

        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);

            when(spyRes.getWriter()).thenReturn(mockPW);

            //run
            addBook.service(spyReq, spyRes);

            //verify
            verify(mockPW).println("<table class=\"tab my-5\" style=\"width:40%;\">\r\n" + "        <tr>\r\n" + "            <td>\r\n" + "                <form action=\"addbook\" method=\"post\">\r\n" + "                    <!-- <label for=\"bookCode\">Book Code : </label><input type=\"text\" name=\"barcode\" id=\"bookCode\" placeholder=\"Enter Book Code\" required><br/> -->\r\n" + "                    <label for=\"bookName\">Book Name : </label> <input type=\"text\" name=\"name\" id=\"bookName\" placeholder=\"Enter Book's name\" required><br/>\r\n" + "                    <label for=\"bookAuthor\">Book Author : </label><input type=\"text\" name=\"author\" id=\"bookAuthor\" placeholder=\"Enter Author's Name\" required><br/>\r\n" + "                    <label for=\"bookPrice\">Book Price : </label><input type=\"number\" name=\"price\" placeholder=\"Enter the Price\" required><br/>\r\n" + "                    <label for=\"bookQuantity\">Book Qnty : </label><input type=\"number\" name=\"quantity\" id=\"bookQuantity\" placeholder=\"Enter the quantity\" required><br/>\r\n" + "                    <input class=\"btn btn-success my-2\" type=\"submit\" value=\" Add Book \">\r\n" + "                </form>\r\n" + "            </td>\r\n" + "        </tr>  \r\n" + "        <!-- <tr>\r\n" + "            <td><a href=\"index.html\">Go Back To Home Page</a></td>\r\n" + "        </tr> -->\r\n" + "    </table>");
        } catch(Exception e){
            System.out.println(e);
            fail();
        }

        //
        // TEST CLAUSE 1: bname == null
        // Since bname is null, isBlank cannot be invoked and therefore guarantees that this is an active clause.
        // CLAUSE IS FALSE
        //

        req = new MockHttpServletRequest();
        req.addParameter(BooksDBConstants.COLUMN_NAME, "notNull"); //ensure bName is not null

        res = new MockHttpServletResponse();

        mockPW = mock(PrintWriter.class);

        spyReq = spy(req);
        spyRes = spy(res);

        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);

            when(spyRes.getWriter()).thenReturn(mockPW);

            //run
            addBook.service(spyReq, spyRes);

            //verify
            verify(mockPW,never()).println("<table class=\"tab my-5\" style=\"width:40%;\">\r\n" + "        <tr>\r\n" + "            <td>\r\n" + "                <form action=\"addbook\" method=\"post\">\r\n" + "                    <!-- <label for=\"bookCode\">Book Code : </label><input type=\"text\" name=\"barcode\" id=\"bookCode\" placeholder=\"Enter Book Code\" required><br/> -->\r\n" + "                    <label for=\"bookName\">Book Name : </label> <input type=\"text\" name=\"name\" id=\"bookName\" placeholder=\"Enter Book's name\" required><br/>\r\n" + "                    <label for=\"bookAuthor\">Book Author : </label><input type=\"text\" name=\"author\" id=\"bookAuthor\" placeholder=\"Enter Author's Name\" required><br/>\r\n" + "                    <label for=\"bookPrice\">Book Price : </label><input type=\"number\" name=\"price\" placeholder=\"Enter the Price\" required><br/>\r\n" + "                    <label for=\"bookQuantity\">Book Qnty : </label><input type=\"number\" name=\"quantity\" id=\"bookQuantity\" placeholder=\"Enter the quantity\" required><br/>\r\n" + "                    <input class=\"btn btn-success my-2\" type=\"submit\" value=\" Add Book \">\r\n" + "                </form>\r\n" + "            </td>\r\n" + "        </tr>  \r\n" + "        <!-- <tr>\r\n" + "            <td><a href=\"index.html\">Go Back To Home Page</a></td>\r\n" + "        </tr> -->\r\n" + "    </table>");
        } catch(Exception e){
            System.out.println(e);
            fail();
        }

        //
        // TEST CLAUSE 2: bname.isBlank
        // Since the clause requires a non-null string, this will be an active clause
        // CLAUSE IS TRUE
        //

        req = new MockHttpServletRequest();
        req.addParameter(BooksDBConstants.COLUMN_NAME, "  "); //ensure bName is blank

        res = new MockHttpServletResponse();

        mockPW = mock(PrintWriter.class);

        spyReq = spy(req);
        spyRes = spy(res);

        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);

            when(spyRes.getWriter()).thenReturn(mockPW);

            //run
            addBook.service(spyReq, spyRes);

            //verify
            verify(mockPW).println("<table class=\"tab my-5\" style=\"width:40%;\">\r\n" + "        <tr>\r\n" + "            <td>\r\n" + "                <form action=\"addbook\" method=\"post\">\r\n" + "                    <!-- <label for=\"bookCode\">Book Code : </label><input type=\"text\" name=\"barcode\" id=\"bookCode\" placeholder=\"Enter Book Code\" required><br/> -->\r\n" + "                    <label for=\"bookName\">Book Name : </label> <input type=\"text\" name=\"name\" id=\"bookName\" placeholder=\"Enter Book's name\" required><br/>\r\n" + "                    <label for=\"bookAuthor\">Book Author : </label><input type=\"text\" name=\"author\" id=\"bookAuthor\" placeholder=\"Enter Author's Name\" required><br/>\r\n" + "                    <label for=\"bookPrice\">Book Price : </label><input type=\"number\" name=\"price\" placeholder=\"Enter the Price\" required><br/>\r\n" + "                    <label for=\"bookQuantity\">Book Qnty : </label><input type=\"number\" name=\"quantity\" id=\"bookQuantity\" placeholder=\"Enter the quantity\" required><br/>\r\n" + "                    <input class=\"btn btn-success my-2\" type=\"submit\" value=\" Add Book \">\r\n" + "                </form>\r\n" + "            </td>\r\n" + "        </tr>  \r\n" + "        <!-- <tr>\r\n" + "            <td><a href=\"index.html\">Go Back To Home Page</a></td>\r\n" + "        </tr> -->\r\n" + "    </table>");
        } catch(Exception e){
            System.out.println(e);
            fail();
        }

        //
        // TEST CLAUSE 2: bname.isBlank
        // Since the clause requires a non-null string, this will be an active clause
        // CLAUSE IS FALSE
        //

        req = new MockHttpServletRequest();
        req.addParameter(BooksDBConstants.COLUMN_NAME, "notBlank"); //ensure bName is not Blank

        res = new MockHttpServletResponse();

        mockPW = mock(PrintWriter.class);

        spyReq = spy(req);
        spyRes = spy(res);

        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);

            when(spyRes.getWriter()).thenReturn(mockPW);

            //run
            addBook.service(spyReq, spyRes);

            //verify
            verify(mockPW,never()).println("<table class=\"tab my-5\" style=\"width:40%;\">\r\n" + "        <tr>\r\n" + "            <td>\r\n" + "                <form action=\"addbook\" method=\"post\">\r\n" + "                    <!-- <label for=\"bookCode\">Book Code : </label><input type=\"text\" name=\"barcode\" id=\"bookCode\" placeholder=\"Enter Book Code\" required><br/> -->\r\n" + "                    <label for=\"bookName\">Book Name : </label> <input type=\"text\" name=\"name\" id=\"bookName\" placeholder=\"Enter Book's name\" required><br/>\r\n" + "                    <label for=\"bookAuthor\">Book Author : </label><input type=\"text\" name=\"author\" id=\"bookAuthor\" placeholder=\"Enter Author's Name\" required><br/>\r\n" + "                    <label for=\"bookPrice\">Book Price : </label><input type=\"number\" name=\"price\" placeholder=\"Enter the Price\" required><br/>\r\n" + "                    <label for=\"bookQuantity\">Book Qnty : </label><input type=\"number\" name=\"quantity\" id=\"bookQuantity\" placeholder=\"Enter the quantity\" required><br/>\r\n" + "                    <input class=\"btn btn-success my-2\" type=\"submit\" value=\" Add Book \">\r\n" + "                </form>\r\n" + "            </td>\r\n" + "        </tr>  \r\n" + "        <!-- <tr>\r\n" + "            <td><a href=\"index.html\">Go Back To Home Page</a></td>\r\n" + "        </tr> -->\r\n" + "    </table>");
        } catch(Exception e){
            System.out.println(e);
            fail();
        }

        //
        // Scenario: Exception is thrown
        // 1 Predicate (Is an exception thrown?)
        //

        //
        // THE PREDICATE IS TRUE
        //

        req = new MockHttpServletRequest();
        req.addParameter(BooksDBConstants.COLUMN_NAME, "notBlank"); //ensure bName is not Blank

        res = new MockHttpServletResponse();

        mockPW = mock(PrintWriter.class);

        spyReq = spy(req);
        spyRes = spy(res);

        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);

            when(spyRes.getWriter()).thenReturn(mockPW);
            when(spyReq.getParameter(BooksDBConstants.COLUMN_PRICE)).thenReturn("NaN");

            //run
            addBook.service(spyReq, spyRes);

            //verify
            verify(mockPW).println("<table class=\"tab\"><tr><td>Failed to Add Books! Fill up CareFully</td></tr></table>");
        } catch(Exception e){
            System.out.println(e);
            fail();
        }

        //
        // THE PREDICATE IS FALSE ( No exceptions thrown)
        //
        try {
            when(bookServMock.addBook(any())).thenReturn("SUCCESS");
        } catch(Exception e){
            System.out.println(e);
            fail();
        }
        req = new MockHttpServletRequest();
        req.addParameter(BooksDBConstants.COLUMN_NAME, "notBlank"); //ensure bName is not Blank

        res = new MockHttpServletResponse();

        mockPW = mock(PrintWriter.class);

        spyReq = spy(req);
        spyRes = spy(res);

        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);

            when(spyRes.getWriter()).thenReturn(mockPW);
            when(spyReq.getParameter(BooksDBConstants.COLUMN_AUTHOR)).thenReturn("Author");
            when(spyReq.getParameter(BooksDBConstants.COLUMN_PRICE)).thenReturn("20");
            when(spyReq.getParameter(BooksDBConstants.COLUMN_QUANTITY)).thenReturn("10");

            //run
            addBook.service(spyReq, spyRes);

            //verify
            verify(mockPW,never()).println("<table class=\"tab\"><tr><td>Failed to Add Books! Fill up CareFully</td></tr></table>");
        } catch(Exception e){
            System.out.println(e);
            fail();
        }

        //
        // Scenario: Successfully added book
        // 1 Predicate, 1 clause
        //

        //
        // Predicate is TRUE
        //

        try {
            when(bookServMock.addBook(any())).thenReturn("SUCCESS");
        } catch(Exception e){
            System.out.println(e);
            fail();
        }

        req = new MockHttpServletRequest();
        req.addParameter(BooksDBConstants.COLUMN_NAME, "notBlank"); //ensure bName is not Blank

        res = new MockHttpServletResponse();

        mockPW = mock(PrintWriter.class);

        spyReq = spy(req);
        spyRes = spy(res);

        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);

            when(spyRes.getWriter()).thenReturn(mockPW);
            when(spyReq.getParameter(BooksDBConstants.COLUMN_AUTHOR)).thenReturn("Author");
            when(spyReq.getParameter(BooksDBConstants.COLUMN_PRICE)).thenReturn("20");
            when(spyReq.getParameter(BooksDBConstants.COLUMN_QUANTITY)).thenReturn("10");

            //run
            addBook.service(spyReq, spyRes);

            //verify
            verify(mockPW).println("<table class=\"tab\"><tr><td>Book Detail Updated Successfully!<br/>Add More Books</td></tr></table>");
        } catch(Exception e){
            System.out.println(e);
            fail();
        }

        //
        // Predicate is FALSE
        //

        try {
            when(bookServMock.addBook(any())).thenReturn("FAILURE");
        } catch(Exception e){
            System.out.println(e);
            fail();
        }

        req = new MockHttpServletRequest();
        req.addParameter(BooksDBConstants.COLUMN_NAME, "notBlank"); //ensure bName is not Blank

        res = new MockHttpServletResponse();

        mockPW = mock(PrintWriter.class);

        spyReq = spy(req);
        spyRes = spy(res);

        try(MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)){
            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.SELLER, spyReq.getSession())).thenReturn(true);

            when(spyRes.getWriter()).thenReturn(mockPW);
            when(spyReq.getParameter(BooksDBConstants.COLUMN_AUTHOR)).thenReturn("Author");
            when(spyReq.getParameter(BooksDBConstants.COLUMN_PRICE)).thenReturn("20");
            when(spyReq.getParameter(BooksDBConstants.COLUMN_QUANTITY)).thenReturn("10");

            //run
            addBook.service(spyReq, spyRes);

            //verify
            verify(mockPW).println("<table class=\"tab\"><tr><td>Failed to Add Books! Fill up CareFully</td></tr></table>");
        } catch(Exception e){
            System.out.println(e);
            fail();
        }
    }

}