package test.pom.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import test.pom.pages.MainPage;


public class Application {

    private WebDriver driver;

    private MainPage mainPage;

    public Application() {
        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
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

}
