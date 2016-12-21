package test.pom.app;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.pom.pages.CartPage;
import test.pom.pages.DuckPage;
import test.pom.pages.MainPage;

public class Application {

    private WebDriver driver;
    private WebDriverWait wait;

    private MainPage mainPage;
    private DuckPage duckPage;
    private CartPage cartPage;

    public Application() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        mainPage = new MainPage(driver);
        duckPage = new DuckPage(driver);
        cartPage = new CartPage(driver);
    }

    public void quit() {
        driver.quit();
    }

    public void login(String login, String password) {
        mainPage.open();
        mainPage.emailField.sendKeys(login);
        mainPage.passwordField.sendKeys(password);
        mainPage.loginButton.click();
    }

    public boolean loginFailed() {
        return mainPage.loginError.getText().contains("Wrong password");
    }

    public boolean loginSuccessful() {
        return mainPage.loginSuccess.getText().contains("You are now logged in");
    }

    public boolean provideBothMessageAppeared() {
        return mainPage.loginError.getText().contains("must provide both");
    }

    public void logout() {
        mainPage.logoutLink.click();
    }

    public void addFirstDuck() {
        mainPage.open();
        mainPage.FirstDuck.click();
        duckPage.addToCartButton.click();
        wait.until(ExpectedConditions.attributeContains(duckPage.quantity, "textContent", String.valueOf(1)));
    }

    public void addSecondDuck() {
        mainPage.open();
        mainPage.SecondDuck.click();
        duckPage.addToCartButton.click();
        wait.until(ExpectedConditions.attributeContains(duckPage.quantity, "textContent", String.valueOf(2)));
    }

    public void addThirdDuck() {
        mainPage.open();
        mainPage.ThirdDuck.click();
        duckPage.addToCartButton.click();
        wait.until(ExpectedConditions.attributeContains(duckPage.quantity, "textContent", String.valueOf(3)));
    }

    public void assertDuckQuantity(int number) {
        mainPage.open();
        Assert.assertTrue(mainPage.quantity.getText().equals(String.valueOf(number)));
    }

    public void removeDuck() {
        cartPage.open();
        cartPage.removeButton.click();
        //wait.until(ExpectedConditions.stalenessOf(cartPage.table));
    }
}
