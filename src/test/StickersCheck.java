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

public class StickersCheck {

    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void checkAllStickers() {

        //open main page of Duck Store
        driver.get("http://localhost/litecart");

        //find all ducks and put them in a list
        List<WebElement> duckList = driver.findElements(By.cssSelector(".content .products .product"));

        //iterate through the list and check if every duck has one and only one sticker
        for (WebElement duck : duckList) {
            Assert.assertTrue(duck.findElements(By.cssSelector(".sticker")).size() == 1);
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
