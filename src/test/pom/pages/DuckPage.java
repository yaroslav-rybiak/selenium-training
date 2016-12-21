package test.pom.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DuckPage extends Page {

    public DuckPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css="button[value='Add To Cart']")
    public WebElement addToCartButton;

    @FindBy(css="span[class=quantity]")
    public WebElement quantity;

    @FindBy(css="span[class=formatted_value]")
    public WebElement value;
}
