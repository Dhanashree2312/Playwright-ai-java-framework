package tests;

import ai.SelfHealingClient;
import ai.SelfHealingResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SelfHealingTest {

    @Test
    public void findAlternativeLocator() {

        SelfHealingClient healingClient =
                new SelfHealingClient();

        // --------------------------------------------------
        // 1. Deliberately incorrect locator
        // --------------------------------------------------

        String failedLocator = "#old-login-button";

        // --------------------------------------------------
        // 2. Current page HTML
        // --------------------------------------------------

        String pageContent = """
                <html>
                    <body>

                        <div class="login-container">

                            <h1>Login</h1>

                            <input
                                id="username"
                                name="username"
                                type="text"
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
                                type="submit">
                                Login
                            </button>

                        </div>

                    </body>
                </html>
                """;

        // --------------------------------------------------
        // 3. Ask AI to find replacement locator
        // --------------------------------------------------

        SelfHealingResponse healingResponse =
                healingClient.findAlternativeLocator(
                        failedLocator,
                        pageContent
                );

        // --------------------------------------------------
        // 4. Print AI response
        // --------------------------------------------------

        System.out.println();
        System.out.println("=================================");
        System.out.println("SELF-HEALING AI RESPONSE");
        System.out.println("=================================");

        System.out.println(
                "Found: "
                        + healingResponse.isFound()
        );

        System.out.println(
                "Locator: "
                        + healingResponse.getLocator()
        );

        System.out.println(
                "Reason: "
                        + healingResponse.getReason()
        );

        // --------------------------------------------------
        // 5. Validate AI found a replacement
        // --------------------------------------------------

        Assert.assertTrue(
                healingResponse.isFound(),
                "AI should find an alternative locator"
        );

        Assert.assertNotNull(
                healingResponse.getLocator(),
                "AI locator should not be null"
        );

        Assert.assertFalse(
                healingResponse.getLocator().isBlank(),
                "AI locator should not be empty"
        );

        // --------------------------------------------------
        // 6. For this controlled example, verify
        //    AI identified the Login button
        // --------------------------------------------------

        Assert.assertTrue(
                healingResponse
                        .getLocator()
                        .contains("login-button"),
                "AI should identify the Login button locator"
        );

        // --------------------------------------------------
        // 7. Cleanup
        // --------------------------------------------------

        healingClient.close();
    }
}