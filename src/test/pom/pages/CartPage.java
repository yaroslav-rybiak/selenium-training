package test.pom.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends Page {

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("http://localhost/litecart/en/checkout");
    }

    @FindBy(css="button[value='Remove']")
    public WebElement removeButton;

    @FindBy(css="#box-checkout-summary")
    public  WebElement table;
}