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

public class DuckCheck {

    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void checkDuck() {

        //open main page of Duck Store
        driver.get("http://localhost/litecart");

        //find a duck from campaigns and remember its stats
        WebElement duck = driver.findElement(By.cssSelector("#box-campaigns .products .product"));
        String mainName = duck.findElement(By.cssSelector(".name")).getText();
        String mainRegularPrice = duck.findElement(By.cssSelector(".regular-price")).getText();
        String mainCampaignPrice = duck.findElement(By.cssSelector(".campaign-price")).getText();

        //also remember styles
        String mainRegularColor = duck.findElement(By.cssSelector(".regular-price")).getCssValue("font-color");
        String mainCampaignColor = duck.findElement(By.cssSelector(".campaign-price")).getCssValue("font-color");

        //open a page with this duck and compare stats
        duck.findElement(By.cssSelector(".link")).click();
        String pageName = driver.findElement(By.cssSelector("#box-product .title")).getText();
        String pageRegularPrice = driver.findElement(By.cssSelector("#box-product .regular-price")).getText();
        String pageCampaignPrice = driver.findElement(By.cssSelector("#box-product .campaign-price")).getText();
        Assert.assertEquals(mainName, pageName);
        Assert.assertEquals(mainRegularPrice, pageRegularPrice);
        Assert.assertEquals(mainCampaignPrice, pageCampaignPrice);

        //also compare styles
        String pageRegularColor = driver.findElement(By.cssSelector("#box-product .regular-price")).getCssValue("font-color");
        String pageCampaignColor = driver.findElement(By.cssSelector("#box-product .campaign-price")).getCssValue("font-color");
        Assert.assertEquals(mainRegularColor, pageRegularColor);
        Assert.assertEquals(mainCampaignColor, pageCampaignColor);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
