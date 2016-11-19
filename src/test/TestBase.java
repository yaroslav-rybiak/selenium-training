import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
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
        //With singleton "@Before" you shouldn't try
        //to close browser after every test
        //use shutdown hook instead
        //it will close browser after all tests
        //driver.quit();
        //driver = null;
    }
}