import org.junit.Test;
import org.junit.Assert;

import org.openqa.selenium.By;

public class CookiesTest extends TestBase {

    @Test
    public void deleteSessionCookie() {

        //Open main page and enter credentials
        driver.get("http://localhost/litecart/");
        driver.findElement(By.name("email")).sendKeys("ivan@dubinin.uk");
        driver.findElement(By.name("password")).sendKeys("ivan@dubinin.uk");
        driver.findElement(By.name("login")).click();

        //Assert that user is authorized
        Assert.assertEquals(driver.findElement(By.cssSelector("#box-account > h3:nth-child(1)")).getText(), "Account");

        //Delete session cookie
        driver.manage().deleteCookieNamed("LCSESSID");
        driver.navigate().refresh();

        //Assert that after cookie deletion user is not authorized
        Assert.assertEquals(driver.findElement(By.cssSelector("#box-account-login > h3:nth-child(1)")).getText(), "Login");
    }

    @Test
    public void deleteAllCookies() {

        //Open main page and enter credentials
        driver.get("http://localhost/litecart/");
        driver.findElement(By.name("email")).sendKeys("ivan@dubinin.uk");
        driver.findElement(By.name("password")).sendKeys("ivan@dubinin.uk");
        driver.findElement(By.name("login")).click();

        //Assert that user is authorized
        Assert.assertEquals(driver.findElement(By.cssSelector("#box-account > h3:nth-child(1)")).getText(), "Account");

        //Delete all cookies
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();

        //Assert that after cookies deletion user is not authorized
        Assert.assertEquals(driver.findElement(By.cssSelector("#box-account-login > h3:nth-child(1)")).getText(), "Login");
    }

}