package mutation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BookMutationTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    Random random;

    @Before
    public void setUp() throws MalformedURLException {

        driver = new ChromeDriver();
        //new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome());
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    @After
    public void tearDown() {
        //driver.quit();
    }

    private String generateAuthor()
    {
        String author;
        author="";
        int lengthauthor=random.nextInt(15);
        for (int i=0;i<=lengthauthor;i++)
        {
            int ch;
            ch=random.nextInt(27);
            if (ch==0)
            {
                author=author+' ';
            }
            else
            {
                author=author+(char)(97+ch);
            }
        }
        return author;
    }

    private String generateName()
    {
        String name;
        name="";
        random=new Random();
        int lengthname=random.nextInt(20);
        for (int i=0;i<=lengthname;i++)
        {
            int ch;
            ch=random.nextInt(27);
            if (ch==0)
            {
                name=name+' ';
            }
            else
            {
                name=name+(char)(97+ch);
            }
        }
        return name;
    }

    private int generateQuantity()
    {
        int quantity=random.nextInt(20);
        return quantity;
    }

    private int generatePrice()
    {
        int price=random.nextInt(2000);
        return price;
    }

    @Test
    public void addNormalBook()
    {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.cssSelector("td > a")).click();
        driver.findElement(By.id("userName")).click();
        driver.findElement(By.id("userName")).sendKeys("admin");
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).sendKeys("admin");
        driver.findElement(By.cssSelector(".AdminLogin")).click();
        driver.findElement(By.id("addbook")).click();
        driver.findElement(By.id("bookName")).click();
        driver.findElement(By.id("bookName")).sendKeys(generateName());
        driver.findElement(By.id("bookAuthor")).click();
        driver.findElement(By.id("bookAuthor")).sendKeys(generateAuthor());
        driver.findElement(By.name("price")).click();
        driver.findElement(By.name("price")).sendKeys(Integer.toString(generatePrice()));
        driver.findElement(By.id("bookQuantity")).click();
        driver.findElement(By.id("bookQuantity")).sendKeys(Integer.toString(generateQuantity()));
        driver.findElement(By.cssSelector(".btn")).click();
    }

    @Test
    public void addAbNormalBook1()
    {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.cssSelector("td > a")).click();
        driver.findElement(By.id("userName")).click();
        driver.findElement(By.id("userName")).sendKeys("admin");
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).sendKeys("admin");
        driver.findElement(By.cssSelector(".AdminLogin")).click();
        driver.findElement(By.id("addbook")).click();
        driver.findElement(By.id("bookName")).click();
        driver.findElement(By.id("bookName")).sendKeys(generateName());
        driver.findElement(By.id("bookAuthor")).click();
        driver.findElement(By.id("bookAuthor")).sendKeys(generateAuthor());
        driver.findElement(By.name("price")).click();
        driver.findElement(By.name("price")).sendKeys(generateName());
        driver.findElement(By.id("bookQuantity")).click();
        driver.findElement(By.id("bookQuantity")).sendKeys(Integer.toString(generateQuantity()));
        driver.findElement(By.cssSelector(".btn")).click();
    }

    @Test
    public void addAbNormalBook2()
    {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.cssSelector("td > a")).click();
        driver.findElement(By.id("userName")).click();
        driver.findElement(By.id("userName")).sendKeys("admin");
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).sendKeys("admin");
        driver.findElement(By.cssSelector(".AdminLogin")).click();
        driver.findElement(By.id("addbook")).click();
        driver.findElement(By.id("bookName")).click();
        driver.findElement(By.id("bookName")).sendKeys(generateName());
        driver.findElement(By.id("bookAuthor")).click();
        driver.findElement(By.id("bookAuthor")).sendKeys(generateAuthor());
        driver.findElement(By.name("price")).click();
        driver.findElement(By.name("price")).sendKeys(Integer.toString(generatePrice()));
        driver.findElement(By.id("bookQuantity")).click();
        driver.findElement(By.id("bookQuantity")).sendKeys(generateName());
        driver.findElement(By.cssSelector(".btn")).click();
    }
    @Test
    public void addAbNormalBook3()
    {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.cssSelector("td > a")).click();
        driver.findElement(By.id("userName")).click();
        driver.findElement(By.id("userName")).sendKeys("admin");
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).sendKeys("admin");
        driver.findElement(By.cssSelector(".AdminLogin")).click();
        driver.findElement(By.id("addbook")).click();
        driver.findElement(By.id("bookName")).click();
        driver.findElement(By.id("bookName")).sendKeys(generateName());
        driver.findElement(By.id("bookAuthor")).click();
        driver.findElement(By.id("bookAuthor")).sendKeys(generateAuthor());
        driver.findElement(By.name("price")).click();
        driver.findElement(By.name("price")).sendKeys(Integer.toString(generatePrice()));
        driver.findElement(By.id("bookQuantity")).click();
        driver.findElement(By.id("bookQuantity")).sendKeys(Integer.toString(-1*generateQuantity()));
        driver.findElement(By.cssSelector(".btn")).click();
    }

    @Test
    public void addAbNormalBook4()
    {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.cssSelector("td > a")).click();
        driver.findElement(By.id("userName")).click();
        driver.findElement(By.id("userName")).sendKeys("admin");
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).sendKeys("admin");
        driver.findElement(By.cssSelector(".AdminLogin")).click();
        driver.findElement(By.id("addbook")).click();
        driver.findElement(By.id("bookName")).click();
        driver.findElement(By.id("bookName")).sendKeys(generateName());
        driver.findElement(By.id("bookAuthor")).click();
        driver.findElement(By.id("bookAuthor")).sendKeys(generateAuthor());
        driver.findElement(By.name("price")).click();
        driver.findElement(By.name("price")).sendKeys(Integer.toString(-1*generatePrice()));
        driver.findElement(By.id("bookQuantity")).click();
        driver.findElement(By.id("bookQuantity")).sendKeys(Integer.toString(generateQuantity()));
        driver.findElement(By.cssSelector(".btn")).click();
    }

    @Test
    public void addAbNormalBook5()
    {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.cssSelector("td > a")).click();
        driver.findElement(By.id("userName")).click();
        driver.findElement(By.id("userName")).sendKeys("admin");
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).sendKeys("admin");
        driver.findElement(By.cssSelector(".AdminLogin")).click();
        driver.findElement(By.id("addbook")).click();
        driver.findElement(By.id("bookName")).click();
        driver.findElement(By.id("bookName")).sendKeys(generateName());
        driver.findElement(By.id("bookAuthor")).click();
        driver.findElement(By.id("bookAuthor")).sendKeys(generateAuthor());
        driver.findElement(By.name("price")).click();
        driver.findElement(By.name("price")).sendKeys(Integer.toString(-1*generatePrice())+".-"+Integer.toString(-1*generatePrice()));
        driver.findElement(By.id("bookQuantity")).click();
        driver.findElement(By.id("bookQuantity")).sendKeys(Integer.toString(generateQuantity()));
        driver.findElement(By.cssSelector(".btn")).click();
    }

    @Test
    public void addAbNormalBook6()
    {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.cssSelector("td > a")).click();
        driver.findElement(By.id("userName")).click();
        driver.findElement(By.id("userName")).sendKeys("admin");
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).sendKeys("admin");
        driver.findElement(By.cssSelector(".AdminLogin")).click();
        driver.findElement(By.id("addbook")).click();
        driver.findElement(By.id("bookName")).click();
        driver.findElement(By.id("bookName")).sendKeys(generateName());
        driver.findElement(By.id("bookAuthor")).click();
        driver.findElement(By.id("bookAuthor")).sendKeys(generateAuthor());
        driver.findElement(By.name("price")).click();
        driver.findElement(By.name("price")).sendKeys(Double.toString(generatePrice()+0.8));
        driver.findElement(By.id("bookQuantity")).click();
        driver.findElement(By.id("bookQuantity")).sendKeys(Integer.toString(generateQuantity()));
        driver.findElement(By.cssSelector(".btn")).click();
    }

    @Test
    public void addAbNormalBook7()
    {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.cssSelector("td > a")).click();
        driver.findElement(By.id("userName")).click();
        driver.findElement(By.id("userName")).sendKeys("admin");
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).sendKeys("admin");
        driver.findElement(By.cssSelector(".AdminLogin")).click();
        driver.findElement(By.id("addbook")).click();
        //driver.findElement(By.id("bookName")).click();
        //driver.findElement(By.id("bookName")).sendKeys(generateName());
        driver.findElement(By.id("bookAuthor")).click();
        driver.findElement(By.id("bookAuthor")).sendKeys((generateAuthor()));
        driver.findElement(By.name("price")).click();
        driver.findElement(By.name("price")).sendKeys(Integer.toString(generatePrice()));
        driver.findElement(By.id("bookQuantity")).click();
        driver.findElement(By.id("bookQuantity")).sendKeys(Integer.toString(generateQuantity()));
        driver.findElement(By.cssSelector(".btn")).click();
    }

    @Test
    public void addAbNormalBook8()
    {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.cssSelector("td > a")).click();
        driver.findElement(By.id("userName")).click();
        driver.findElement(By.id("userName")).sendKeys("admin");
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).sendKeys("admin");
        driver.findElement(By.cssSelector(".AdminLogin")).click();
        driver.findElement(By.id("addbook")).click();
        driver.findElement(By.id("bookName")).click();
        driver.findElement(By.id("bookName")).sendKeys(generateName());
        //driver.findElement(By.id("bookAuthor")).click();
        //driver.findElement(By.id("bookAuthor")).sendKeys(Integer.toString(generateAuthor());
        driver.findElement(By.name("price")).click();
        driver.findElement(By.name("price")).sendKeys(Integer.toString(generatePrice()));
        driver.findElement(By.id("bookQuantity")).click();
        driver.findElement(By.id("bookQuantity")).sendKeys(Integer.toString(generateQuantity()));
        driver.findElement(By.cssSelector(".btn")).click();
    }
    @Test
    public void addAbNormalBook9()
    {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.cssSelector("td > a")).click();
        driver.findElement(By.id("userName")).click();
        driver.findElement(By.id("userName")).sendKeys("admin");
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).sendKeys("admin");
        driver.findElement(By.cssSelector(".AdminLogin")).click();
        driver.findElement(By.id("addbook")).click();
        driver.findElement(By.id("bookName")).click();
        driver.findElement(By.id("bookName")).sendKeys(generateName());
        driver.findElement(By.id("bookAuthor")).click();
        driver.findElement(By.id("bookAuthor")).sendKeys(generateAuthor());
        //driver.findElement(By.name("price")).click();
        //driver.findElement(By.name("price")).sendKeys(Integer.toString(generatePrice()));
        driver.findElement(By.id("bookQuantity")).click();
        driver.findElement(By.id("bookQuantity")).sendKeys(Integer.toString(generateQuantity()));
        driver.findElement(By.cssSelector(".btn")).click();
    }
    @Test
    public void addAbNormalBook10()
    {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.cssSelector("td > a")).click();
        driver.findElement(By.id("userName")).click();
        driver.findElement(By.id("userName")).sendKeys("admin");
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).sendKeys("admin");
        driver.findElement(By.cssSelector(".AdminLogin")).click();
        driver.findElement(By.id("addbook")).click();
        driver.findElement(By.id("bookName")).click();
        driver.findElement(By.id("bookName")).sendKeys(generateName());
        driver.findElement(By.id("bookAuthor")).click();
        driver.findElement(By.id("bookAuthor")).sendKeys(generateAuthor());
        driver.findElement(By.name("price")).click();
        driver.findElement(By.name("price")).sendKeys(Integer.toString(generatePrice()));
        //driver.findElement(By.id("bookQuantity")).click();
        //driver.findElement(By.id("bookQuantity")).sendKeys(Integer.toString(generateQuantity()));
        driver.findElement(By.cssSelector(".btn")).click();
    }

    @Test
    public void addAbNormalBook11()
    {
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.cssSelector("td > a")).click();
        driver.findElement(By.id("userName")).click();
        driver.findElement(By.id("userName")).sendKeys("admin");
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).sendKeys("admin");
        driver.findElement(By.cssSelector(".AdminLogin")).click();
        driver.findElement(By.id("addbook")).click();
        driver.findElement(By.id("bookName")).click();
        driver.findElement(By.id("bookName")).sendKeys(generateName());
        driver.findElement(By.id("bookAuthor")).click();
        driver.findElement(By.id("bookAuthor")).sendKeys(Integer.toString(generatePrice()));
        driver.findElement(By.name("price")).click();
        driver.findElement(By.name("price")).sendKeys(Integer.toString(generatePrice()));
        driver.findElement(By.id("bookQuantity")).click();
        driver.findElement(By.id("bookQuantity")).sendKeys(Integer.toString(generateQuantity()));
        driver.findElement(By.cssSelector(".btn")).click();
    }
}
