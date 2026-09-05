package pages;

import com.microsoft.playwright.Page;
import framework.config.ConfigReader;

public class HomePage {

    private final Page page;

    public HomePage(Page page) {
        this.page = page;
    }

    public void navigate() {
        page.navigate(ConfigReader.get("baseUrl"));
    }

    public String getTitle() {
        return page.title();
    }

    public String getUrl() {
        return page.url();
    }
}