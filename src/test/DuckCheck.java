import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.concurrent.TimeUnit;
import java.util.List;

public class DuckCheck {

    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void checkDuck() {

        //open main page of Duck Store
        driver.get("http://localhost/litecart");

        //find a duck from campaigns and remember its stats
        WebElement duck = driver.findElement(By.cssSelector("#box-campaigns .products .product"));
        String main_name = duck.findElement(By.cssSelector(".name")).getText();
        String main_price1 = duck.findElement(By.cssSelector(".regular-price")).getText();
        String main_price2 = duck.findElement(By.cssSelector(".campaign-price")).getText();

        //open a page with this duck and compare stats
        duck.findElement(By.cssSelector(".link")).click();
        String page_name = driver.findElement(By.cssSelector("#box-product .title")).getText();
        String page_price1 = driver.findElement(By.cssSelector("#box-product .regular-price")).getText();
        String page_price2 = driver.findElement(By.cssSelector("#box-product .campaign-price")).getText();
        Assert.assertEquals(main_name, page_name);
        Assert.assertEquals(main_price1, page_price1);
        Assert.assertEquals(main_price2, page_price2);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
