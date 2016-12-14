package test;

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
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.ArrayList;

public class AddNewProduct {

    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @Test
    public void addProduct() {

        //login as admin
        driver.get("http://localhost/litecart/admin/?category_id=0&app=catalog&doc=edit_product");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        String success = driver.findElement(By.id("notices")).getText();
        Assert.assertTrue(success.contains("You are now logged in as admin"));

        //create new product
        String productName = randomEnglishString();

        //tab General
        driver.findElement(By.cssSelector("#tab-general label input[value='1']")).click();
        driver.findElement(By.name("name[en]")).sendKeys(productName);
        driver.findElement(By.name("name[ru]")).sendKeys(randomRussianString());

        //tab Information
        driver.findElement(By.cssSelector("div[class=tabs] a[href='#tab-information']")).click();
        driver.findElement(By.cssSelector("select[name=manufacturer_id]")).sendKeys("ACME" + Keys.ENTER);
        driver.findElement(By.name("short_description[en]")).sendKeys(randomEnglishString());
        driver.findElement(By.name("short_description[ru]")).sendKeys(randomRussianString());

        //tab Prices
        driver.findElement(By.cssSelector("div[class=tabs] a[href='#tab-prices']")).click();
        driver.findElement(By.cssSelector("[name=purchase_price]")).sendKeys("100");
        driver.findElement(By.cssSelector("[name=purchase_price]")).click();
        driver.findElement(By.cssSelector("option[value=USD]")).click();

        //save changes
        driver.findElement(By.cssSelector("form > p button[name=save]")).click();

        //check that product is successfully added
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog");
        List<WebElement> productItems = driver.findElements(By.cssSelector("tr[class=row] td a:not([title=Edit])"));
        List<String> products = new ArrayList<>();
        for (WebElement product : productItems) {
            products.add(product.getText());
        }
        Assert.assertTrue(products.stream().anyMatch(str -> str.trim().equals(productName)));

        //logout
        driver.get("http://localhost/litecart/admin/logout.php");
        Assert.assertTrue(driver.getCurrentUrl().equals("http://localhost/litecart/admin/login.php"));
    }

    private String randomEnglishString() {
        String word = "";
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int firstNumber = (int)(Math.random() * 26);
        char firstLetter = alphabet.charAt(firstNumber);
        word += Character.toUpperCase(firstLetter);

        for (int i = 0; i < 6; i++) {
            int number = (int)(Math.random() * 26);
            char letter = alphabet.charAt(number);
            word += letter;
        }

        return word;
    }

    private String randomRussianString() {
        String word = "";
        String alphabet = "абвгдежзиклмнопрстуфхцчшщыюя";
        int firstNumber = (int)(Math.random() * 27);
        char firstLetter = alphabet.charAt(firstNumber);
        word += Character.toUpperCase(firstLetter);

        for (int i = 0; i < 6; i++) {
            int number = (int)(Math.random() * 27);
            char letter = alphabet.charAt(number);
            word += letter;
        }

        return word;
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
