package mutation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UserMutationTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    Random random;

    private String generateEmail()
    {
        String email;
        email="";
        random = new Random();
        int lengthemail = random.nextInt(8);
        for (int i = 0; i <= lengthemail; i++) {
            int ch;
            ch = random.nextInt(27);
            email = email + (char) (97 + ch);
        }
        email=email+"@gmail.com";
        return email;
    }

    private String generatePassword()
    {
        String password;
        password="";
        random = new Random();
        int lengthpassword = random.nextInt(15);
        for (int i = 0; i <= lengthpassword; i++) {
            int ch;
            ch = random.nextInt(27);
            password = password + (char) (97 + ch);
        }
        return password;
    }

    private String generateFirstName()
    {
        String first_name;
        first_name="";
        random = new Random();
        int lengthfirstname = random.nextInt(8);
        for (int i = 0; i <= lengthfirstname; i++) {
            int ch;
            ch = random.nextInt(27);
            first_name = first_name + (char) (97 + ch);
        }
        return first_name;
    }

    private String generateLastName()
    {
        String last_name;
        last_name="";
        random = new Random();
        int lengthlastname = random.nextInt(8);
        for (int i = 0; i <= lengthlastname; i++) {
            int ch;
            ch = random.nextInt(27);
            last_name = last_name + (char) (97 + ch);

        }
        return last_name;
    }

    private String generateAddress()
    {
        String address;
        address="";
        random = new Random();
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
        return address;
    }

    private long generateMobileNumber()
    {
        long phno;
        String phone_number;
        int lengthph;
        lengthph=10;
        phone_number="";
        random = new Random();
        for (int i=1;i<=lengthph;i++)
        {
            int n=random.nextInt(10);
            phone_number=phone_number+Integer.toString(n);
        }
        phno=Long.parseLong(phone_number);
        return phno;
    }

    @Before
    public void setUp() throws MalformedURLException {

        driver = new ChromeDriver();
        //new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome());
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    @After
    public void tearDown() {
       // driver.quit();
    }

    @Test
    public void registerNormalCustomer() {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).sendKeys(generateEmail());
        driver.findElement(By.id("passWord")).click();
        driver.findElement(By.id("passWord")).sendKeys(generatePassword());
        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).sendKeys(generateFirstName());
        driver.findElement(By.id("lastName")).click();
        driver.findElement(By.id("lastName")).sendKeys(generateLastName());
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys(generateAddress());
        driver.findElement(By.id("phno")).click();
        driver.findElement(By.id("phno")).sendKeys(Long.toString(generateMobileNumber()));
        driver.findElement(By.name("acceptance")).click();
        driver.findElement(By.cssSelector(".btn")).click();

    }

    @Test
    public void registerAbNormalCustomer1() {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Register")).click();
        //driver.findElement(By.id("Email")).click();
        //driver.findElement(By.id("Email")).sendKeys(generateEmail());
        driver.findElement(By.id("passWord")).click();
        driver.findElement(By.id("passWord")).sendKeys(generatePassword());
        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).sendKeys(generateFirstName());
        driver.findElement(By.id("lastName")).click();
        driver.findElement(By.id("lastName")).sendKeys(generateLastName());
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys(generateAddress());
        driver.findElement(By.id("phno")).click();
        driver.findElement(By.id("phno")).sendKeys(Long.toString(generateMobileNumber()));
        driver.findElement(By.name("acceptance")).click();
        driver.findElement(By.cssSelector(".btn")).click();

    }
    @Test
    public void registerAbNormalCustomer2() {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).sendKeys(generateEmail());
     //   driver.findElement(By.id("passWord")).click();
     //   driver.findElement(By.id("passWord")).sendKeys(generatePassword());
        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).sendKeys(generateFirstName());
        driver.findElement(By.id("lastName")).click();
        driver.findElement(By.id("lastName")).sendKeys(generateLastName());
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys(generateAddress());
        driver.findElement(By.id("phno")).click();
        driver.findElement(By.id("phno")).sendKeys(Long.toString(generateMobileNumber()));
        driver.findElement(By.name("acceptance")).click();
        driver.findElement(By.cssSelector(".btn")).click();

    }
    @Test
    public void registerAbNormalCustomer3() {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).sendKeys(generateEmail());
        driver.findElement(By.id("passWord")).click();
        driver.findElement(By.id("passWord")).sendKeys(generatePassword());
        //driver.findElement(By.id("firstName")).click();
        //driver.findElement(By.id("firstName")).sendKeys(generateFirstName());
        driver.findElement(By.id("lastName")).click();
        driver.findElement(By.id("lastName")).sendKeys(generateLastName());
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys(generateAddress());
        driver.findElement(By.id("phno")).click();
        driver.findElement(By.id("phno")).sendKeys(Long.toString(generateMobileNumber()));
        driver.findElement(By.name("acceptance")).click();
        driver.findElement(By.cssSelector(".btn")).click();

    }
    @Test
    public void registerAbNormalCustomer4() {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).sendKeys(generateEmail());
        driver.findElement(By.id("passWord")).click();
        driver.findElement(By.id("passWord")).sendKeys(generatePassword());
        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).sendKeys(generateFirstName());
        //driver.findElement(By.id("lastName")).click();
        //driver.findElement(By.id("lastName")).sendKeys(generateLastName());
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys(generateAddress());
        driver.findElement(By.id("phno")).click();
        driver.findElement(By.id("phno")).sendKeys(Long.toString(generateMobileNumber()));
        driver.findElement(By.name("acceptance")).click();
        driver.findElement(By.cssSelector(".btn")).click();

    }
    @Test
    public void registerAbNormalCustomer5() {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).sendKeys(generateEmail());
        driver.findElement(By.id("passWord")).click();
        driver.findElement(By.id("passWord")).sendKeys(generatePassword());
        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).sendKeys(generateFirstName());
        driver.findElement(By.id("lastName")).click();
        driver.findElement(By.id("lastName")).sendKeys(generateLastName());
        //driver.findElement(By.id("address")).click();
        //driver.findElement(By.id("address")).sendKeys(generateAddress());
        driver.findElement(By.id("phno")).click();
        driver.findElement(By.id("phno")).sendKeys(Long.toString(generateMobileNumber()));
        driver.findElement(By.name("acceptance")).click();
        driver.findElement(By.cssSelector(".btn")).click();

    }
    @Test
    public void registerAbNormalCustomer6() {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).sendKeys(generateEmail());
        driver.findElement(By.id("passWord")).click();
        driver.findElement(By.id("passWord")).sendKeys(generatePassword());
        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).sendKeys(generateFirstName());
        driver.findElement(By.id("lastName")).click();
        driver.findElement(By.id("lastName")).sendKeys(generateLastName());
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys(generateAddress());
        //driver.findElement(By.id("phno")).click();
        //driver.findElement(By.id("phno")).sendKeys(Long.toString(generateMobileNumber()));
        driver.findElement(By.name("acceptance")).click();
        driver.findElement(By.cssSelector(".btn")).click();

    }

    @Test
    public void registerAbNormalCustomer7() {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).sendKeys(generateEmail());
        driver.findElement(By.id("passWord")).click();
        driver.findElement(By.id("passWord")).sendKeys(generatePassword());
        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).sendKeys(generateFirstName());
        driver.findElement(By.id("lastName")).click();
        driver.findElement(By.id("lastName")).sendKeys(generateLastName());
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys(generateAddress());
        driver.findElement(By.id("phno")).click();
        driver.findElement(By.id("phno")).sendKeys(generatePassword());
        driver.findElement(By.name("acceptance")).click();
        driver.findElement(By.cssSelector(".btn")).click();

    }

    @Test
    public void registerAbNormalCustomer8() {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).sendKeys(Long.toString(generateMobileNumber()));
        driver.findElement(By.id("passWord")).click();
        driver.findElement(By.id("passWord")).sendKeys(generatePassword());
        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).sendKeys(generateFirstName());
        driver.findElement(By.id("lastName")).click();
        driver.findElement(By.id("lastName")).sendKeys(generateLastName());
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys(generateAddress());
        driver.findElement(By.id("phno")).click();
        driver.findElement(By.id("phno")).sendKeys(Long.toString(generateMobileNumber()));
        driver.findElement(By.name("acceptance")).click();
        driver.findElement(By.cssSelector(".btn")).click();

    }

    @Test
    public void registerAbNormalCustomer9() {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).sendKeys(generatePassword());
        driver.findElement(By.id("passWord")).click();
        driver.findElement(By.id("passWord")).sendKeys(generatePassword());
        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).sendKeys(generateFirstName());
        driver.findElement(By.id("lastName")).click();
        driver.findElement(By.id("lastName")).sendKeys(generateLastName());
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys(generateAddress());
        driver.findElement(By.id("phno")).click();
        driver.findElement(By.id("phno")).sendKeys(Long.toString(generateMobileNumber()));
        driver.findElement(By.name("acceptance")).click();
        driver.findElement(By.cssSelector(".btn")).click();

    }

    @Test
    public void registerAbNormalCustomer10() {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).sendKeys(Long.toString(generateMobileNumber()));
        driver.findElement(By.id("passWord")).click();
        driver.findElement(By.id("passWord")).sendKeys(generatePassword());
        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).sendKeys(generateFirstName());
        driver.findElement(By.id("lastName")).click();
        driver.findElement(By.id("lastName")).sendKeys(generateLastName());
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys(generateAddress());
        driver.findElement(By.id("phno")).click();
        driver.findElement(By.id("phno")).sendKeys(Long.toString(-1*generateMobileNumber()));
        driver.findElement(By.name("acceptance")).click();
        driver.findElement(By.cssSelector(".btn")).click();

    }
    @Test
    public void registerAbNormalCustomer11() {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).sendKeys(Long.toString(generateMobileNumber()));
        driver.findElement(By.id("passWord")).click();
        driver.findElement(By.id("passWord")).sendKeys(generatePassword());
        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).sendKeys(generateFirstName());
        driver.findElement(By.id("lastName")).click();
        driver.findElement(By.id("lastName")).sendKeys(generateLastName());
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys(generateAddress());
        driver.findElement(By.id("phno")).click();
        driver.findElement(By.id("phno")).sendKeys(Long.toString(generateMobileNumber())+".5");
        driver.findElement(By.name("acceptance")).click();
        driver.findElement(By.cssSelector(".btn")).click();

    }
    @Test
    public void registerAbNormalCustomer12() {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).sendKeys(generateEmail());
        driver.findElement(By.id("passWord")).click();
        driver.findElement(By.id("passWord")).sendKeys(generatePassword());
        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).sendKeys(generateFirstName());
        driver.findElement(By.id("lastName")).click();
        driver.findElement(By.id("lastName")).sendKeys(generateLastName());
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys(generateAddress());
        driver.findElement(By.id("phno")).click();
        driver.findElement(By.id("phno")).sendKeys(Long.toString(generateMobileNumber()/10000));
        driver.findElement(By.name("acceptance")).click();
        driver.findElement(By.cssSelector(".btn")).click();

    }
}
