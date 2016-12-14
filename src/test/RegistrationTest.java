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

public class RegistrationTest {

    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void NewRegistration() {

        //create new email and password
        String email = randomEmail();
        String password = randomString();

        //open main page of Duck Store and click registration link
        driver.get("http://localhost/litecart");
        driver.findElement(By.linkText("New customers click here")).click();
        Assert.assertTrue(driver.getCurrentUrl().equals("http://localhost/litecart/en/create_account"));

        //perform registration
        driver.findElement(By.cssSelector("[name=firstname]")).sendKeys(randomString());
        driver.findElement(By.cssSelector("[name=lastname]")).sendKeys(randomString());
        driver.findElement(By.cssSelector("[name=address1]")).sendKeys(randomString());
        driver.findElement(By.cssSelector("[name=postcode]")).sendKeys("02001");
        driver.findElement(By.cssSelector("[name=city]")).sendKeys(randomString());
        driver.findElement(By.cssSelector("[class=select2-selection__arrow]")).click();
        driver.findElement(By.cssSelector("[class=select2-search__field]")).sendKeys("Ukraine" + Keys.ENTER);
        driver.findElement(By.cssSelector("[name=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("[name=phone]")).sendKeys(randomPhone());
        driver.findElement(By.cssSelector("[name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("[name=confirmed_password]")).sendKeys(password);
        driver.findElement(By.cssSelector("[name=create_account]")).click();

        //logout, login and logout again
        driver.findElement(By.linkText("Logout")).click();
        driver.findElement(By.cssSelector("[name=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("[name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("[name=login]")).click();
        driver.findElement(By.linkText("Logout")).click();

    }

    private String randomEmail() {
        String email = "";
        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < 6; i++) {
            int number = (int)(Math.random() * 26);
            char letter = alphabet.charAt(number);
            email += letter;
        }

        email += "@fake.mail";
        return email;
    }

    private String randomString() {
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

    private String randomPhone() {
        String phone = "";
        String alphabet = "0123456789";

        for (int i = 0; i < 7; i++) {
            int number = (int)(Math.random() * 9);
            char letter = alphabet.charAt(number);
            phone += letter;
        }

        return "+380" + phone;
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
