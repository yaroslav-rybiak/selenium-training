package test.pom.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends Page {

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("http://localhost/litecart/");
    }

    @FindBy(css="input[name='email']")
    public WebElement emailField;

    @FindBy(css="input[name='password']")
    public WebElement passwordField;

    @FindBy(css="button[name='login']")
    public WebElement loginButton;

    @FindBy(css="div[class='notice errors']")
    public WebElement loginError;

    @FindBy(css="div[class='notice success']")
    public WebElement loginSuccess;

    @FindBy(linkText="Logout")
    public WebElement logoutLink;
}
