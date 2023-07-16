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
public class LogoutTest {
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
        driver.findElement(By.id("happy")).click();
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.id("CaptchaDiv")).click();
        driver.findElement(By.id("userName")).click();
        driver.findElement(By.id("userName")).sendKeys("shashi");
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).sendKeys("shashi");
        driver.findElement(By.id("CaptchaInput")).click();
        driver.findElement(By.id("CaptchaInput")).sendKeys(driver.findElement(By.id("CaptchaDiv")).getText());
        driver.findElement(By.cssSelector(".btn")).click();
    }
    @TearDown(Level.Invocation)
    public void tearDown() {
        driver.quit();
    }
    @Benchmark
    @Fork(2)
    public void logout() {
        driver.findElement(By.id("logout")).click();
    }
}
