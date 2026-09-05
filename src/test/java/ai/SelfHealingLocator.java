package ai;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;

public class SelfHealingLocator {

    private final SelfHealingClient healingClient;

    public SelfHealingLocator() {
        healingClient = new SelfHealingClient();
    }

    public void click(Page page, String locator) {

        try {

            System.out.println();
            System.out.println("Trying locator: " + locator);

            page.locator(locator)
                    .click(
                            new Locator.ClickOptions()
                                    .setTimeout(3000)
                    );

            System.out.println(
                    "✅ Original locator worked: " + locator
            );

        } catch (PlaywrightException e) {

            System.out.println();
            System.out.println(
                    "❌ Locator failed: " + locator
            );

            System.out.println(
                    "🤖 Starting AI self-healing..."
            );

            String pageContent =
                    getRelevantButtonHtml(page);

            System.out.println(
                    "📄 HTML sent to AI: "
                            + pageContent.length()
            );

            SelfHealingResponse healingResponse =
                    healingClient.findAlternativeLocator(
                            locator,
                            pageContent
                    );

            healAndClick(
                    page,
                    locator,
                    healingResponse
            );
        }
    }

    public void fill(
            Page page,
            String locator,
            String value) {

        try {

            System.out.println();
            System.out.println(
                    "Trying locator for fill: "
                            + locator
            );

            page.locator(locator)
                    .fill(
                            value,
                            new Locator.FillOptions()
                                    .setTimeout(3000)
                    );

            System.out.println(
                    "✅ Original locator worked: "
                            + locator
            );

        } catch (PlaywrightException e) {

            System.out.println();
            System.out.println(
                    "❌ Locator failed: "
                            + locator
            );

            System.out.println(
                    "🤖 Starting AI self-healing..."
            );

            String pageContent =
                    getRelevantInputHtml(page);

            System.out.println(
                    "📄 HTML sent to AI: "
                            + pageContent.length()
            );

            SelfHealingResponse healingResponse =
                    healingClient.findAlternativeLocator(
                            locator,
                            pageContent
                    );

            if (!healingResponse.isFound()) {

                throw new RuntimeException(
                        "AI could not find a replacement locator. "
                                + healingResponse.getReason()
                );
            }

            String healedLocator =
                    healingResponse.getLocator();

            validateLocator(
                    page,
                    healedLocator
            );

            System.out.println();
            System.out.println(
                    "================================="
            );
            System.out.println(
                    "AI HEALED LOCATOR"
            );
            System.out.println(
                    "================================="
            );

            System.out.println(
                    "Original: " + locator
            );

            System.out.println(
                    "Healed: " + healedLocator
            );

            System.out.println(
                    "Reason: "
                            + healingResponse.getReason()
            );

            System.out.println();
            System.out.println(
                    "Retrying fill with healed locator..."
            );

            page.locator(healedLocator)
                    .fill(value);

            System.out.println(
                    "✅ Self-healing successful!"
            );
        }
    }

    private String getRelevantButtonHtml(Page page) {

        int buttonCount =
                page.locator("button").count();

        StringBuilder html =
                new StringBuilder();

        for (int i = 0; i < buttonCount; i++) {

            String buttonHtml =
                    page.locator("button")
                            .nth(i)
                            .evaluate(
                                    "element => element.outerHTML"
                            )
                            .toString();

            html.append(buttonHtml)
                    .append("\n");
        }

        return html.toString();
    }

    private String getRelevantInputHtml(Page page) {

        int inputCount =
                page.locator("input").count();

        StringBuilder html =
                new StringBuilder();

        for (int i = 0; i < inputCount; i++) {

            String inputHtml =
                    page.locator("input")
                            .nth(i)
                            .evaluate(
                                    "element => element.outerHTML"
                            )
                            .toString();

            html.append(inputHtml)
                    .append("\n");
        }

        return html.toString();
    }

    private void validateLocator(
            Page page,
            String healedLocator) {

        if (healedLocator == null
                || healedLocator.isBlank()) {

            throw new RuntimeException(
                    "AI returned an empty replacement locator"
            );
        }

        int count =
                page.locator(healedLocator)
                        .count();

        if (count == 0) {

            throw new RuntimeException(
                    "AI locator does not match any element: "
                            + healedLocator
            );
        }

        if (count > 1) {

            throw new RuntimeException(
                    "AI locator matches multiple elements: "
                            + healedLocator
            );
        }
    }

    private void healAndClick(
            Page page,
            String originalLocator,
            SelfHealingResponse healingResponse) {

        if (!healingResponse.isFound()) {

            throw new RuntimeException(
                    "AI could not find a replacement locator. "
                            + healingResponse.getReason()
            );
        }

        String healedLocator =
                healingResponse.getLocator();

        validateLocator(
                page,
                healedLocator
        );

        System.out.println();
        System.out.println(
                "================================="
        );
        System.out.println(
                "AI HEALED LOCATOR"
        );
        System.out.println(
                "================================="
        );

        System.out.println(
                "Original: "
                        + originalLocator
        );

        System.out.println(
                "Healed: "
                        + healedLocator
        );

        System.out.println(
                "Reason: "
                        + healingResponse.getReason()
        );

        System.out.println();
        System.out.println(
                "Retrying with healed locator..."
        );

        page.locator(healedLocator)
                .click();

        System.out.println(
                "✅ Self-healing successful!"
        );
    }

    public void close() {
        healingClient.close();
    }
}