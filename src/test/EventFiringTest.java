package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class EventFiringTest {

    public static class MyListener extends AbstractWebDriverEventListener {

        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println(by);
        }

        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println(by + " found");
        }

        @Override
        public void onException(Throwable throwable, WebDriver driver) {
            System.out.println(throwable);
        }

    }


    public EventFiringWebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");

        driver = new EventFiringWebDriver(new ChromeDriver(options));
        driver.register(new MyListener());

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void GoogleSearch() {

        driver.get("http://google.com/ncr");
        WebElement searchField = driver.findElement(By.cssSelector("[name=q]"));
        searchField.sendKeys("EventFiringTest");

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
