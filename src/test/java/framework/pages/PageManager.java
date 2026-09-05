package framework.pages;

import com.microsoft.playwright.Page;
import pages.HomePage;
import pages.LoginPage;

public class PageManager {

    private final Page page;

    private HomePage homePage;
    private LoginPage loginPage;

    public PageManager(Page page) {
        this.page = page;
    }

    public HomePage getHomePage() {

        if (homePage == null) {
            homePage = new HomePage(page);
        }

        return homePage;
    }

    public LoginPage getLoginPage() {

        if (loginPage == null) {
            loginPage = new LoginPage(page);
        }

        return loginPage;
    }
}