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
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.ArrayList;

public class MenuTest {

    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void openAllMenuItems() {

        //login as admin
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        String success = driver.findElement(By.id("notices")).getText();
        Assert.assertTrue(success.contains("You are now logged in as admin"));

        //create a list of all main menu items
        List<WebElement> menuElementList = driver.findElements(By.cssSelector("#box-apps-menu-wrapper a"));

        //create a list of all menu link texts
        List<String> menuItemList = new ArrayList<>();
        for (WebElement menuElement : menuElementList) {
            menuItemList.add(menuElement.getText());
        }

        //iterate through all menu items
        for (String menuItem : menuItemList) {

            //click on menu item and print result
            System.out.println(menuItem);
            driver.findElement(By.linkText(menuItem)).click();

            //assert that page has header
            Assert.assertTrue(driver.findElements(By.cssSelector("#content h1") ).size() != 0);

            //create a list of all submenu items
            //not all menus have submenus
            //check that there is a <li> inside menu item
            if (driver.findElements(By.cssSelector("#box-apps-menu-wrapper > ul > li") ).size() != 0) {

                //create a list of all submenu items
                List<WebElement> subMenuElementList = driver.findElements(By.cssSelector(".docs a"));

                //create a list of all submenu link texts
                List<String> subMenuItemList = new ArrayList<>();
                for (WebElement subMenuElement : subMenuElementList) {
                    subMenuItemList.add(subMenuElement.getText());
                }

                //iterate through all submenu items
                for (String subMenuItem : subMenuItemList) {

                    //click on submenu item and print result
                    System.out.println("\t" + subMenuItem);
                    driver.findElement(By.linkText(subMenuItem)).click();

                    //assert that page has header
                    Assert.assertTrue(driver.findElements(By.cssSelector("#content h1") ).size() != 0);
                }
            }
        }

        //logout
        driver.get("http://localhost/litecart/admin/logout.php");
        Assert.assertTrue(driver.getCurrentUrl().equals("http://localhost/litecart/admin/login.php"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
