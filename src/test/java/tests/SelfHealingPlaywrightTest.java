package tests;

import ai.SelfHealingLocator;
import com.microsoft.playwright.*;
import org.testng.annotations.Test;

public class SelfHealingPlaywrightTest {

    @Test
    public void selfHealFailedLocator() {

        Playwright playwright = null;
        Browser browser = null;
        SelfHealingLocator selfHealingLocator = null;

        try {

            playwright = Playwright.create();

            browser =
                    playwright.chromium().launch(
                            new BrowserType.LaunchOptions()
                                    .setHeadless(false)
                    );

            BrowserContext context =
                    browser.newContext();

            Page page =
                    context.newPage();

            String html = """
                    <html>
                        <head>
                            <title>Self Healing Demo</title>
                        </head>

                        <body>

                            <h1>Login Page</h1>

                            <input
                                id="username"
                                name="username"
                                placeholder="Username"
                            />

                            <input
                                id="password"
                                name="password"
                                type="password"
                                placeholder="Password"
                            />

                            <button
                                id="login-button"
                                name="login"
                                type="button">
                                Login
                            </button>

                        </body>
                    </html>
                    """;

            page.setContent(html);

            /*
             * Deliberately incorrect locator.
             *
             * Actual button:
             * id="login-button"
             */
            String failedLocator =
                    "#old-login-button";

            selfHealingLocator =
                    new SelfHealingLocator();

            selfHealingLocator.click(
                    page,
                    failedLocator
            );

        } finally {

            if (selfHealingLocator != null) {
                selfHealingLocator.close();
            }

            if (browser != null) {
                browser.close();
            }

            if (playwright != null) {
                playwright.close();
            }
        }
    }
}