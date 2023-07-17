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
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ViewBookServletTest extends TestCase {
    @Mock
    BookService mockBookServ;

    @InjectMocks
    ViewBookServlet viewBookServlet = new ViewBookServlet();

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

        try (MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)) {

            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(false);

            viewBookServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("CustomerLogin.html");

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 1 (FALSE): logged in as customer
        // Predicate 2 (FALSE): NOT exceptions thrown
        //
        reset(spyReq, spyRes, mockPW, mockRD, mockBookServ);

        try (MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)) {

            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(true);
            when(spyRes.getWriter()).thenReturn(mockPW);
            viewBookServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("CustomerHome.html");
            verify(mockPW).println("</div>"
                    + "<div style='float:auto'><form action=\"cart\" method=\"post\">"
                    + "<input type='submit' class=\"btn btn-success\" name='cart' value='Proceed to Checkout'/></form>"
                    + "    </div>");

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 2 (True): exceptions thrown
        //
        reset(spyReq, spyRes, mockPW, mockRD, mockBookServ);

        try (MockedStatic<StoreUtil> storeMock = mockStatic(StoreUtil.class)) {

            storeMock.when(() -> StoreUtil.isLoggedIn(UserRole.CUSTOMER, spyReq.getSession())).thenReturn(true);
            when(spyRes.getWriter()).thenReturn(mockPW);
            when(spyReq.getRequestDispatcher("CustomerHome.html")).thenReturn(mockRD);
            doThrow(new ServletException()).when(mockRD).include(spyReq,spyRes);
            viewBookServlet.service(spyReq, spyRes);


            verify(mockPW, never()).println("</div>"
                    + "<div style='float:auto'><form action=\"cart\" method=\"post\">"
                    + "<input type='submit' class=\"btn btn-success\" name='cart' value='Proceed to Checkout'/></form>"
                    + "    </div>");

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testAddBookToCard(){
        //
        // Predicate 1 (True): session.getAttribute("qty_" + bCode) != null
        // Predicate 2 (True): bQty > 0
        //

        MockHttpSession ses = new MockHttpSession();
        MockHttpSession spySes = spy(ses);

        Book mockBook = mock(Book.class);
        when(mockBook.getAuthor()).thenReturn("author");
        when(mockBook.getName()).thenReturn("name");
        when(mockBook.getPrice()).thenReturn(5.0);
        when(mockBook.getBarcode()).thenReturn("code");
        when(mockBook.getQuantity()).thenReturn(2);

        try{

            when(spySes.getAttribute("qty_code")).thenReturn(2);
            String result = viewBookServlet.addBookToCard(spySes,mockBook);

            verify(spySes, atMost(3)).getAttribute("qty_code");

            String button = "<form action=\"viewbook\" method=\"post\">"
                    + "<input type='hidden' name = 'selectedBookId' value = code>"
                    + "<input type='hidden' name='qty_code' value='1'/>"
                    + "<form method='post' action='cart'>"
                    + "<button type='submit' name='removeFromCart' class=\"glyphicon glyphicon-minus btn btn-danger\"></button> "
                    + "<input type='hidden' name='selectedBookId' value='code'/>"
                    + 2
                    + " <button type='submit' name='addToCart' class=\"glyphicon glyphicon-plus btn btn-success\"></button></form>"
                    + "";

            assertEquals("<div class=\"card\">\r\n"
                    + "                <div class=\"row card-body\">\r\n"
                    + "                    <img class=\"col-sm-6\" src=\"logo.png\" alt=\"Card image cap\">\r\n"
                    + "                    <div class=\"col-sm-6\">\r\n"
                    + "                        <h5 class=\"card-title text-success\">name</h5>\r\n"
                    + "                        <p class=\"card-text\">\r\n"
                    + "                        Author: <span class=\"text-primary\" style=\"font-weight:bold;\"> author</span><br>\r\n"
                    + "                        </p>\r\n"
                    + "                        \r\n"
                    + "                    </div>\r\n"
                    + "                </div>\r\n"
                    + "                <div class=\"row card-body\">\r\n"
                    + "                    <div class=\"col-sm-6\">\r\n"
                    + "                        <p class=\"card-text\">\r\n"
                    + "                        <span>Id: code</span>\r\n"
                    + "<br><span class=\"text-danger\">Only 2 items left</span>\r\n"

                    + "                        </p>\r\n"
                    + "                    </div>\r\n"
                    + "                    <div class=\"col-sm-6\">\r\n"
                    + "                        <p class=\"card-text\">\r\n"
                    + "                        Price: <span style=\"font-weight:bold; color:green\"> &#8377; "
                    + 5.0
                    + " </span>\r\n"
                    + "                        </p>\r\n"
                    + button
                    + "                    </div>\r\n"
                    + "                </div>\r\n"
                    + "            </div>", result);

        }catch(Exception e){
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 1 (FALSE): NOT session.getAttribute("qty_" + bCode) != null
        // Predicate 2 (FALSE): bQty > 0
        //
        reset(spySes);
        try{
            when(mockBook.getQuantity()).thenReturn(0);
            when(spySes.getAttribute("qty_code")).thenReturn(null);
            String result = viewBookServlet.addBookToCard(spySes,mockBook);

            verify(spySes, atMost(2)).getAttribute("qty_code");

            String button = "<p class=\"btn btn-danger\">Out Of Stock</p>\r\n";

            assertEquals("<div class=\"card\">\r\n"
                    + "                <div class=\"row card-body\">\r\n"
                    + "                    <img class=\"col-sm-6\" src=\"logo.png\" alt=\"Card image cap\">\r\n"
                    + "                    <div class=\"col-sm-6\">\r\n"
                    + "                        <h5 class=\"card-title text-success\">name</h5>\r\n"
                    + "                        <p class=\"card-text\">\r\n"
                    + "                        Author: <span class=\"text-primary\" style=\"font-weight:bold;\"> author</span><br>\r\n"
                    + "                        </p>\r\n"
                    + "                        \r\n"
                    + "                    </div>\r\n"
                    + "                </div>\r\n"
                    + "                <div class=\"row card-body\">\r\n"
                    + "                    <div class=\"col-sm-6\">\r\n"
                    + "                        <p class=\"card-text\">\r\n"
                    + "                        <span>Id: code</span>\r\n"
                    + "<br><span class=\"text-danger\">Only 0 items left</span>\r\n"

                    + "                        </p>\r\n"
                    + "                    </div>\r\n"
                    + "                    <div class=\"col-sm-6\">\r\n"
                    + "                        <p class=\"card-text\">\r\n"
                    + "                        Price: <span style=\"font-weight:bold; color:green\"> &#8377; "
                    + 5.0
                    + " </span>\r\n"
                    + "                        </p>\r\n"
                    + button
                    + "                    </div>\r\n"
                    + "                </div>\r\n"
                    + "            </div>", result);

        }catch(Exception e){
            e.printStackTrace();
            fail();
        }

    }
}