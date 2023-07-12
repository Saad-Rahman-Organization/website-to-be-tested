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
public class RemoveBook {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    Random random;
    String name;
    String author;

    String id;
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
        random = new Random();
        int lengthname = random.nextInt(20);
        for (int i = 0; i <= lengthname; i++) {
            int ch;
            ch = random.nextInt(27);
            if (ch == 0) {
                name = name + ' ';
            } else {
                name = name + (char) (97 + ch);
            }
        }
        int lengthauthor = random.nextInt(15);
        for (int i = 0; i <= lengthauthor; i++) {
            int ch;
            ch = random.nextInt(27);
            if (ch == 0) {
                author = author + ' ';
            } else {
                author = author + (char) (97 + ch);
            }
        }
        int lengthid = random.nextInt(5);
        for (int i = 0; i <= lengthid; i++) {
            int ch;
            ch = random.nextInt(27);
            if (ch == 0) {
                id = id + ' ';
            } else {
                id = id + (char) (97 + ch);
            }
        }
        price = random.nextInt(2000);
        quantity = random.nextInt(20);
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
        BookServiceImpl bookService = new BookServiceImpl();
        try
        {
        List<Book> book = bookService.getAllBooks();
        for (Book b : book) {
            if (b.getName().toString().equals(name) && b.getAuthor().toString().equals(author) && b.getPrice() == price && b.getQuantity() == quantity) {
                id = b.getBarcode();
            }
        }
    }
        catch (StoreException e)
        {
            ;
        }
    }
    @TearDown(Level.Invocation)
    public void tearDown() {
        name="";
        author="";
        id="";
        driver.quit();
    }
    @Benchmark
    @Fork(2)
    public void removebook() {
        driver.findElement(By.id("removebook")).click();
        driver.findElement(By.id("bookCode")).click();
        driver.findElement(By.id("bookCode")).sendKeys(id);
        driver.findElement(By.cssSelector(".btn")).click();
      
    }
}
