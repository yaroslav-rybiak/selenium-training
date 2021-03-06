package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/*
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import java.io.File;
*/

public class AllBrowsersTest {

    private WebDriver driver;

    @Before
    public void start() {

        //Chrome browser options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);

        /*
        //Firefox browser options NEW scheme for standard build
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(FirefoxDriver.MARIONETTE, true);
        driver = new FirefoxDriver(caps);

        //Firefox browser options for nightly build
        driver = new FirefoxDriver(new FirefoxBinary(new File("C:\\Program Files\\Nightly\\firefox.exe")),
                new FirefoxProfile());

        //Firefox browser options OLD scheme for firefox 45
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(FirefoxDriver.MARIONETTE, false);
        driver = new FirefoxDriver(new FirefoxBinary(new File("C:\\Program Files (x86)\\Mozilla Firefox 45\\firefox.exe")),
                new FirefoxProfile(), caps);

        //IE browser options
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        driver = new InternetExplorerDriver(caps);
        */
    }

    @Test
    public void loginAsAdminUsingName() {
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        String success = driver.findElement(By.id("notices")).getText();
        Assert.assertTrue(success.contains("You are now logged in as admin"));
        driver.get("http://localhost/litecart/admin/logout.php");
        Assert.assertTrue(driver.getCurrentUrl().equals("http://localhost/litecart/admin/login.php"));
    }

    @Test
    public void loginAsAdminUsingCSS() {
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.cssSelector("[name=username]")).sendKeys("admin");
        driver.findElement(By.cssSelector("[name=password]")).sendKeys("admin");
        driver.findElement(By.cssSelector("[name=login]")).click();
        //we can use [name=login] or [type=submit] with this button
        //driver.findElement(By.cssSelector("[type=submit]")).click();
        String success = driver.findElement(By.cssSelector("#notices")).getText();
        // we can use id "#notices" or tag+class combination "div.success" to find this text
        //String success = driver.findElement(By.cssSelector("div.success")).getText();
        Assert.assertTrue(success.contains("You are now logged in as admin"));
        driver.get("http://localhost/litecart/admin/logout.php");
        Assert.assertTrue(driver.getCurrentUrl().equals("http://localhost/litecart/admin/login.php"));
    }

    @Test
    public void loginAsAdminUsingXpath() {
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.xpath("//*[@name='username']")).sendKeys("admin");
        driver.findElement(By.xpath("//*[@name='password']")).sendKeys("admin");
        driver.findElement(By.xpath("//*[@name='login']")).click();
        String success = driver.findElement(By.xpath("//*[@id='notices']")).getText();
        Assert.assertTrue(success.contains("You are now logged in as admin"));
        driver.get("http://localhost/litecart/admin/logout.php");
        Assert.assertTrue(driver.getCurrentUrl().equals("http://localhost/litecart/admin/login.php"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}