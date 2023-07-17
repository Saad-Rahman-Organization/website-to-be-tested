package perf;

import com.bittercode.model.Book;
import com.bittercode.model.StoreException;
import com.bittercode.service.impl.BookServiceImpl;
import org.openjdk.jmh.annotations.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class CustomerRegisterTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    Random random;
    String first_name;
    String last_name;

    String email;
    String password;
    String address;
    long phno;



    @Setup(Level.Invocation)
    public void setUp() throws MalformedURLException {

        driver = new ChromeDriver();
        //new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome());
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        driver.get("http://localhost:8080/onlinebookstore/");

        random = new Random();
        int lengthfirstname = random.nextInt(8);
        for (int i = 0; i <= lengthfirstname; i++) {
            int ch;
            ch = random.nextInt(27);
            first_name = first_name + (char) (97 + ch);
        }
        int lengthlastname = random.nextInt(8);
        for (int i = 0; i <= lengthlastname; i++) {
            int ch;
            ch = random.nextInt(27);
            last_name = last_name + (char) (97 + ch);

        }
        int lengthemail = random.nextInt(8);
        for (int i = 0; i <= lengthemail; i++) {
            int ch;
            ch = random.nextInt(27);
            email = email + (char) (97 + ch);
        }
        email=email+"@gmail.com";
        int lengthpassword = random.nextInt(15);
        for (int i = 0; i <= lengthpassword; i++) {
            int ch;
            ch = random.nextInt(27);
            password = password + (char) (97 + ch);
        }
        int lengthaddress=random.nextInt(20);
        for (int i=0;i<=lengthaddress;i++)
        {
            int ch;
            ch=random.nextInt(27);
            if (ch==0)
            {
                address=address+' ';
            }
            else
            {
                address=address+(char)(97+ch);
            }
        }
        String phone_number;
        int lengthph;
        lengthph=10;
        phone_number="";
        for (int i=1;i<=lengthph;i++)
        {
            int n=random.nextInt(10);
            phone_number=phone_number+Integer.toString(n);
        }
        phno=Long.parseLong(phone_number);
    }





    @TearDown(Level.Invocation)
    public void tearDown() {
        first_name="";
        last_name="";
        email="";
        address="";
        password="";
        phno=0l;
        driver.quit();
    }
    @Benchmark
    @Fork(2)
    public void registercustomer() {
        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("passWord")).click();
        driver.findElement(By.id("passWord")).sendKeys(password);
        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).sendKeys(first_name);
        driver.findElement(By.id("lastName")).click();
        driver.findElement(By.id("lastName")).sendKeys(last_name);
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys(address);
        driver.findElement(By.id("phno")).click();
        driver.findElement(By.id("phno")).sendKeys(Long.toString(phno));
        driver.findElement(By.name("acceptance")).click();
        driver.findElement(By.cssSelector(".btn")).click();

    }
}
