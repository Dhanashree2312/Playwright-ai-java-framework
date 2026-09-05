package ai;

import com.google.gson.Gson;
import com.microsoft.playwright.APIResponse;

public class SelfHealingClient {

    private final OllamaClient ollamaClient;
    private final Gson gson;

    public SelfHealingClient() {
        ollamaClient = new OllamaClient();
        gson = new Gson();
    }

    public SelfHealingResponse findAlternativeLocator(
            String failedLocator,
            String pageContent) {

        String systemPrompt = """
                You are an expert Playwright automation engineer.

                Your task is to find a replacement locator for a failed
                Playwright locator.

                Analyze the provided HTML and identify the element that the
                failed locator was intended to target.

                IMPORTANT:
                Return ONLY valid JSON.
                Do not return Markdown.
                Do not use ```json.
                Do not provide explanations outside the JSON.

                The locator MUST be compatible with:
                page.locator(locator)

                Therefore return ONLY:
                - CSS selectors
                - XPath selectors

                Prefer locators in this order:

                1. id
                2. data-testid or data-test
                3. name
                4. unique CSS selector
                5. XPath

                Do NOT return:
                - getByRole()
                - getByText()
                - getByLabel()
                - Java code
                - JavaScript code
                - explanations

                Required response format:

                {
                    "found": true,
                    "locator": "CSS or XPath locator",
                    "reason": "short explanation"
                }

                If the element cannot be identified:

                {
                    "found": false,
                    "locator": "",
                    "reason": "element could not be identified"
                }
                """;

        String userPrompt = """
                Failed Playwright locator:

                %s

                Relevant page HTML:

                %s

                Find the best replacement locator.
                Return ONLY the required JSON.
                """.formatted(
                failedLocator,
                pageContent
        );

        APIResponse response =
                ollamaClient.ask(
                        systemPrompt,
                        userPrompt
                );

        if (response.status() != 200) {
            throw new RuntimeException(
                    "Ollama request failed. Status: "
                            + response.status()
            );
        }

        OllamaResponse ollamaResponse =
                gson.fromJson(
                        response.text(),
                        OllamaResponse.class
                );

        if (ollamaResponse == null
                || ollamaResponse.getMessage() == null) {

            throw new RuntimeException(
                    "Invalid response received from Ollama"
            );
        }

        String aiContent =
                ollamaResponse
                        .getMessage()
                        .getContent();

        if (aiContent == null || aiContent.isBlank()) {
            throw new RuntimeException(
                    "Ollama returned empty AI content"
            );
        }

        System.out.println();
        System.out.println("=================================");
        System.out.println("RAW AI CONTENT");
        System.out.println("=================================");
        System.out.println(aiContent);

        aiContent = cleanJson(aiContent);

        System.out.println();
        System.out.println("=================================");
        System.out.println("CLEANED AI JSON");
        System.out.println("=================================");
        System.out.println(aiContent);

        try {

            return gson.fromJson(
                    aiContent,
                    SelfHealingResponse.class
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "AI did not return valid self-healing JSON.\n"
                            + "AI response:\n"
                            + aiContent,
                    e
            );
        }
    }

    private String cleanJson(String content) {

        content = content.trim();

        if (content.startsWith("```")) {

            int firstNewLine =
                    content.indexOf('\n');

            if (firstNewLine != -1) {
                content =
                        content.substring(
                                firstNewLine + 1
                        );
            }

            int lastFence =
                    content.lastIndexOf("```");

            if (lastFence != -1) {
                content =
                        content.substring(
                                0,
                                lastFence
                        );
            }
        }

        content = content.trim();

        int firstBrace =
                content.indexOf('{');

        int lastBrace =
                content.lastIndexOf('}');

        if (firstBrace != -1
                && lastBrace != -1
                && lastBrace > firstBrace) {

            content =
                    content.substring(
                            firstBrace,
                            lastBrace + 1
                    );
        }

        return content.trim();
    }

    public void close() {
        ollamaClient.close();
    }
}