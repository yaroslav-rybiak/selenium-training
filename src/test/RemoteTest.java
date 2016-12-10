package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class RemoteTest {

    private WebDriver driver;

    @Before
    public void start() throws MalformedURLException {
        DesiredCapabilities capability = DesiredCapabilities.firefox();
        driver = new RemoteWebDriver(new URL("http://192.168.0.104:4444/wd/hub"), capability);
        //driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), capability);
    }

    @Test
    public void addProduct() throws Exception {
        driver.get("http://ya.ru");
    }
    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
