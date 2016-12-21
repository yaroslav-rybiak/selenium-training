package test.pom.tests;

import org.junit.Test;

public class ShoppingCartTest extends TestBase {

    @Test
    public void addRemoveItems() {
        app.addFirstDuck();
        app.assertDuckQuantity(1);

        app.addSecondDuck();
        app.assertDuckQuantity(2);

        app.addThirdDuck();
        app.assertDuckQuantity(3);

        app.removeDuck();
        app.assertDuckQuantity(2);

        app.removeDuck();
        app.assertDuckQuantity(1);

        app.removeDuck();
        app.assertDuckQuantity(0);
    }
}
