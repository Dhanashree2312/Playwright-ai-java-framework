package tests;

import framework.base.BaseTest;
import framework.utils.TestDataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(
            dataProvider = "loginData",
            dataProviderClass = TestDataProvider.class
    )
    public void loginTest(
            String email,
            String password) {

        System.out.println(
                "Email: " + email
        );

        System.out.println(
                "Password: " + password
        );

        pageManager
                .getLoginPage()
                .navigate();

        pageManager
                .getLoginPage()
                .enterEmail(email);

        pageManager
                .getLoginPage()
                .enterPassword(password);

        pageManager
                .getLoginPage()
                .clickLogin();
    }
}