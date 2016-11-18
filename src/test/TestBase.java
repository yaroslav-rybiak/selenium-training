import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TestBase {

    //Creating singleton
    public static WebDriver driver;
    public static WebDriverWait wait;

    @Before
    public void start() {

        //Singleton magic
        if (driver != null) {
            return;
        }

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(FirefoxDriver.MARIONETTE, true);
        driver = new FirefoxDriver(caps);
        System.out.println(((HasCapabilities) driver).getCapabilities());
        wait = new WebDriverWait(driver, 10);

        //implicit wait
        //This waits up to 10 seconds before throwing NoSuchElementException exception
        //or if it finds the element will return it in 0 - 10 seconds.
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //shutdown hook
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> { driver.quit(); driver = null; }));
    }

    @After
    public void stop() {
        //You shouldn't try to shut down browser after every test
        //driver.quit();
        //driver = null;
    }
}