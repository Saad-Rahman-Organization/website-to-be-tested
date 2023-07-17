package perf;

import org.openjdk.jmh.annotations.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import java.util.*;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class AddBookTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    Random random;
    String name;
    String author;
    int price;
    int quantity;

    @Setup(Level.Invocation)
    public void setUp() throws MalformedURLException {

        driver = new ChromeDriver();
        //new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome());
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.cssSelector("td > a")).click();
        driver.findElement(By.id("userName")).click();
        driver.findElement(By.id("userName")).sendKeys("admin");
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).sendKeys("admin");
        driver.findElement(By.cssSelector(".AdminLogin")).click();
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
        price=random.nextInt(2000);
        quantity=random.nextInt(20);
    }
    @TearDown(Level.Invocation)
    public void tearDown() {
        name="";
        author="";
        driver.quit();
    }
    @Benchmark
    @Fork(2)
    public void addbook() {
        driver.findElement(By.id("addbook")).click();
        driver.findElement(By.id("bookName")).click();
        driver.findElement(By.id("bookName")).sendKeys(name);
        driver.findElement(By.id("bookAuthor")).click();
        driver.findElement(By.id("bookAuthor")).sendKeys(author);
        driver.findElement(By.name("price")).click();
        driver.findElement(By.name("price")).sendKeys(Integer.toString(price));
        driver.findElement(By.id("bookQuantity")).click();
        driver.findElement(By.id("bookQuantity")).sendKeys(Integer.toString(quantity));
        driver.findElement(By.cssSelector(".btn")).click();
    }
}
