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

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class AdminLogin {

    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @Setup(Level.Invocation)
    public void setUp() throws MalformedURLException {

        driver = new ChromeDriver();
        //new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome());
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        driver.get("http://localhost:8080/onlinebookstore/");
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.cssSelector("td > a")).click();
    }

    @TearDown(Level.Invocation)
    public void tearDown() {
        driver.quit();
    }

    @Benchmark
    @Fork(2)
    public void login() {
        driver.findElement(By.id("userName")).click();
        driver.findElement(By.id("userName")).sendKeys("admin");
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).sendKeys("admin");
        driver.findElement(By.cssSelector(".AdminLogin")).click();
    }
}
