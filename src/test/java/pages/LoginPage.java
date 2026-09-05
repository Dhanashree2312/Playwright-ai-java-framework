package pages;

import ai.SelfHealingLocator;
import com.microsoft.playwright.Page;
import framework.config.ConfigReader;

public class LoginPage {

    private final Page page;
    private final SelfHealingLocator selfHealingLocator;

    public LoginPage(Page page) {
        this.page = page;
        this.selfHealingLocator =
                new SelfHealingLocator();
    }

    public void navigate() {

        page.navigate(
                ConfigReader.get("baseUrl")
                        + "/auth/login"
        );
    }

    public void enterEmail(String email) {

        selfHealingLocator.fill(
                page,
                "[data-test='email']",
                email
        );
    }

    public void enterPassword(String password) {

        selfHealingLocator.fill(
                page,
                "[data-test='password']",
                password
        );
    }

    public void clickLogin() {

        selfHealingLocator.click(
                page,
                "[data-test='login-submit']"
        );
    }

    public void closeSelfHealing() {

        selfHealingLocator.close();
    }
}