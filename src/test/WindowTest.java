import jdk.internal.org.objectweb.asm.Handle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.ArrayList;

public class WindowTest {

    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @Test
    public void countryEdit() {

        //login as admin
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        String success = driver.findElement(By.id("notices")).getText();
        Assert.assertTrue(success.contains("You are now logged in as admin"));

        //remember active window
        String parentWindow = driver.getWindowHandle();

        //open "add new country" page
        driver.findElement(By.cssSelector("td#content > div > a[class=button]")).click();

        //create a list of all external links
        List<WebElement> externalLinks = driver.findElements(By.xpath("//i[contains(@class, 'fa-external-link')]/parent::a"));

        //open and close every link
        for (WebElement link : externalLinks) {
            link.click();
            
            Set<String> windowsSet = driver.getWindowHandles();
            for (String window : windowsSet) {
                if (!window.equals(parentWindow)) {
                    driver.switchTo().window(window);
                    System.out.println("New tab is opened: " + driver.getCurrentUrl());
                    driver.close();
                }
            }

            driver.switchTo().window(parentWindow);
            System.out.println("Returned to parent tab: " + driver.getCurrentUrl());
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
