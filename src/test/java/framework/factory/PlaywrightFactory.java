package framework.factory;

import com.microsoft.playwright.*;
import framework.config.ConfigReader;

public class PlaywrightFactory {

    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> context =new ThreadLocal<>();
    private static final ThreadLocal<Page> page = new ThreadLocal<>();

    public static void initBrowser() {

        playwright.set(Playwright.create());

        String browserType = ConfigReader.get("browser");

        boolean headless =
                Boolean.parseBoolean(
                        ConfigReader.get("headless")
                );

        BrowserType.LaunchOptions options =
                new BrowserType.LaunchOptions()
                        .setHeadless(headless);

        switch (browserType.toLowerCase()) {

            case "chromium":

                browser.set(
                        playwright.get()
                                .chromium()
                                .launch(options)
                );

                break;

            case "firefox":

                browser.set(
                        playwright.get()
                                .firefox()
                                .launch(options)
                );

                break;

            case "webkit":

                browser.set(
                        playwright.get()
                                .webkit()
                                .launch(options)
                );

                break;

            default:

                throw new IllegalArgumentException(
                        "Unsupported browser: " + browserType
                );
        }

        context.set(browser.get().newContext());

        page.set(context.get().newPage());
    }

    public static Page getPage(){
        return page.get();
    }

    public static void closeBrowser() {

        if (page.get() != null) {
            page.get().close();
            page.remove();
        }

        if (context.get() != null) {
            context.get().close();
            context.remove();
        }

        if (browser.get() != null) {
            browser.get().close();
            browser.remove();
        }

        if (playwright.get() != null) {
            playwright.get().close();
            playwright.remove();
        }
    }
}