import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ShoppingCartTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void AddRemoveItems() {

        //create a list of three ducks from "Latest Products" section
        List<String> ducks = new ArrayList<>();
        ducks.add("#box-latest-products .products .product:nth-child(1)");
        ducks.add("#box-latest-products .products .product:nth-child(2)");
        ducks.add("#box-latest-products .products .product:nth-child(3)");

        //add three ducks to cart
        for (int i = 0; i < 3; i++) {

            //open main page
            driver.get("http://localhost/litecart/en/");

            //click a duck from "Latest Products section"
            driver.findElement(By.cssSelector(ducks.get(i))).click();

            //find cart and assert that it has a right quantity of ducks
            WebElement quantity = driver.findElement(By.cssSelector("span[class=quantity]"));
            Assert.assertTrue(quantity.getText().equals(String.valueOf(i)));

            //add duck to cart
            driver.findElement(By.cssSelector("button[value='Add To Cart']")).click();

            //wait until quantity increments
            wait.until(ExpectedConditions.attributeContains(quantity, "textContent", String.valueOf(i+1)));
        }

        //open cart
        driver.findElement(By.linkText("Checkout »")).click();

        //remove ducks from cart one by one and check that table changes every time
        for (int i = 0; i < 3; i++) {
            driver.findElement(By.cssSelector("button[value='Remove']")).click();
            wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.cssSelector("#box-checkout-summary"))));
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
