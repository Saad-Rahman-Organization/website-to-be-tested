package servlets;

import com.bittercode.constant.ResponseCode;
import com.bittercode.constant.db.UsersDBConstants;
import com.bittercode.model.StoreException;
import com.bittercode.model.User;
import com.bittercode.service.UserService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.RequestDispatcher;
import java.io.PrintWriter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CustomerRegisterServletTest extends TestCase {

    @Mock
    UserService mockUserService;

    @InjectMocks
    CustomerRegisterServlet customerRegisterServlet = new CustomerRegisterServlet();

    @Test
    public void testService(){
        //
        // Predicate 1 (FALSE): NOT exceptions thrown
        // Predicate 2 (TRUE): ResponseCode.SUCCESS.name().equalsIgnoreCase(respCode)
        //

        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addParameter(UsersDBConstants.COLUMN_PHONE, "1111111111");
        MockHttpServletResponse res = new MockHttpServletResponse();

        MockHttpServletRequest spyReq = spy(req);
        MockHttpServletResponse spyRes = spy(res);

        RequestDispatcher mockRD = mock(RequestDispatcher.class);
        PrintWriter mockPW = mock(PrintWriter.class);

        try{
            when(mockUserService.register(any(),any())).thenReturn(ResponseCode.SUCCESS.name());
            customerRegisterServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("CustomerLogin.html");

        }catch(Exception e){
            e.printStackTrace();
            fail();
        }

        //
        // Predicate 2 (FALSE): NOT ResponseCode.SUCCESS.name().equalsIgnoreCase(respCode)
        //

        reset(spyRes, spyReq, mockPW, mockRD);
        try{
            when(mockUserService.register(any(),any())).thenReturn(ResponseCode.FAILURE.name());
            customerRegisterServlet.service(spyReq, spyRes);

            verify(spyReq).getRequestDispatcher("CustomerRegister.html");

        }catch(Exception e){
            System.out.println(e);
            fail();
        }

        //
        // Predicate 1 (True): exception thrown
        //

        reset(spyRes, spyReq, mockPW, mockRD);
        try{
            doThrow(new StoreException("Mock Exception")).when(mockUserService).register(any(),any());
            customerRegisterServlet.service(spyReq, spyRes);

            verify(spyReq, never()).getRequestDispatcher("CustomerRegister.html");
            verify(spyReq, never()).getRequestDispatcher("CustomerHome.html");

        }catch(Exception e){
            System.out.println(e);
            fail();
        }
    }



}