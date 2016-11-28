import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.ArrayList;

public class CountriesTest {

    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    //1) на странице http://localhost/litecart/admin/?app=countries&doc=countries
    //а) проверить, что страны расположены в алфавитном порядке
    public void checkCountriesOrder() {

        //login as admin
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        String success = driver.findElement(By.id("notices")).getText();
        Assert.assertTrue(success.contains("You are now logged in as admin"));

        //open countries tab
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");

        //create a list of all country elements
        List<WebElement> countryElements = driver.findElements(By.cssSelector("[class=dataTable] a:not([title=Edit])"));

        //create a list of all country names
        List<String> countries = new ArrayList<>();
        for (WebElement country : countryElements) {
            countries.add(country.getAttribute("innerText"));
        }

        //check that items in a list are in alphabetical order
        List<String> copy = countries;
        Collections.sort(countries);
        Assert.assertEquals(countries, copy);

        //logout
        driver.get("http://localhost/litecart/admin/logout.php");
        Assert.assertTrue(driver.getCurrentUrl().equals("http://localhost/litecart/admin/login.php"));
    }

    @Test
    //1) на странице http://localhost/litecart/admin/?app=countries&doc=countries
    //б) для тех стран, у которых количество зон отлично от нуля -- открыть страницу этой страны и там проверить,
    //что зоны расположены в алфавитном порядке
    public void checkZonesInCountriesOrder() {

        //login as admin
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        String success = driver.findElement(By.id("notices")).getText();
        Assert.assertTrue(success.contains("You are now logged in as admin"));

        //open countries tab
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");

        //create a list of all table rows
        List<WebElement> countryElements = driver.findElements(By.cssSelector("[class=dataTable] [class=row]"));

        //create a list of every country with zones
        List<String> countriesWithZones = new ArrayList<>();
        for (WebElement country : countryElements) {
            if (!country.findElement(By.cssSelector("tbody > tr > td:nth-child(6)")).getText().equals("0")) {
                countriesWithZones.add(country.findElement(By.cssSelector("a:not([title=Edit])")).getAttribute("innerText"));
            }
        }

        //open every country with zones and check order of zones
        for (String country : countriesWithZones) {
            driver.findElement(By.linkText(country)).click();
            List<WebElement> zoneElements = driver.findElements(By.cssSelector("[id=table-zones] > tbody > tr > td:nth-child(3) > input"));
            List<String> zones = new ArrayList<>();
            for (WebElement zone : zoneElements) {
                zones.add(zone.getAttribute("value"));
            }
            List<String> copy = zones;
            Collections.sort(zones);
            Assert.assertEquals(zones, copy);
            driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        }

        //logout
        driver.get("http://localhost/litecart/admin/logout.php");
        Assert.assertTrue(driver.getCurrentUrl().equals("http://localhost/litecart/admin/login.php"));
    }

    @Test
    //2) на странице http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones
    //зайти в каждую из стран и проверить, что зоны расположены в алфавитном порядке
    public void checkZonesOrder() {

        //login as admin
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        String success = driver.findElement(By.id("notices")).getText();
        Assert.assertTrue(success.contains("You are now logged in as admin"));

        //open geo tab
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");

        //create a list of country elements
        List<WebElement> countryElements = driver.findElements(By.cssSelector("[class=dataTable] a:not([title=Edit])"));

        //create a list of all country names
        List<String> countries = new ArrayList<>();
        for (WebElement country : countryElements) {
            countries.add(country.getAttribute("innerText"));
        }

        //open every country and check order of zones
        for (String country : countries) {
            driver.findElement(By.linkText(country)).click();

            List<WebElement> zoneElements = driver.findElements(By.cssSelector("[id=table-zones] > tbody > tr > td:nth-child(3) > select > option[selected=selected]"));
            List<String> zones = new ArrayList<>();
            for (WebElement zone : zoneElements) {
                zones.add(zone.getAttribute("text"));
                System.out.println(zone.getAttribute("text"));
            }
            List<String> copy = zones;
            Collections.sort(zones);
            Assert.assertEquals(zones, copy);
            driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
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
