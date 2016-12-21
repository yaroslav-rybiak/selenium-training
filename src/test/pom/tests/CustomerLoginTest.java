package test.pom.tests;

import org.junit.Assert;
import org.junit.Test;

public class CustomerLoginTest extends TestBase {

    @Test
    public void failedLoginAsCustomer() {
        app.login("failed", "login");
        Assert.assertTrue(app.loginFailed());
    }

    @Test
    public void successfulLoginAsCustomer() {
        app.login("ivan@dubinin.com", "ivan@dubinin.com");
        Assert.assertTrue(app.loginSuccessful());
        app.logout();
    }

    @Test
    public void loginWithoutPassword() {
        app.login("ivan@dubinin.com", "");
        Assert.assertTrue(app.provideBothMessageAppeared());
    }
}
