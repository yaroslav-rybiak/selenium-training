package test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.util.ArrayList;
import java.util.List;

public class BrowserLogsTest {

    private WebDriver driver;

    @Before
    public void start() {
        DesiredCapabilities capability = DesiredCapabilities.firefox();
        driver = new ChromeDriver(capability);
    }

    @Test
    public void getBrowserLogs() {
        //login as admin
        driver.get("http://localhost/litecart/admin/?category_id=0&app=catalog&doc=edit_product");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        String success = driver.findElement(By.id("notices")).getText();
        Assert.assertTrue(success.contains("You are now logged in as admin"));

        //open catalog
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");

        //create a list of all ducks elements
        List<WebElement> duckElements = driver.findElements(By.cssSelector("[class=dataTable] a:not([title=Edit])[href*='edit_product']"));

        //create a list of all ducks
        List<String> ducks = new ArrayList<>();
        for (WebElement duck : duckElements) {
            ducks.add(duck.getAttribute("innerText"));
        }

        //open every duck page and check logs
        for (String duck : ducks) {
            driver.findElement(By.linkText(duck)).click();
            driver.manage().logs().get("browser").forEach(l -> System.out.println(l));
            driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
