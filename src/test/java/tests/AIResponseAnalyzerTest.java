package tests;

import ai.AIAnalysisResponse;
import ai.OllamaClient;
import ai.OllamaResponse;
import com.google.gson.Gson;
import com.microsoft.playwright.APIResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.JsonUtils;

public class AIResponseAnalyzerTest {

    @Test
    public void analyzeInvalidApiResponse() {

        OllamaClient ollamaClient = new OllamaClient();

        // --------------------------------------------------
        // 1. API response we want AI to analyze
        // --------------------------------------------------

        String apiResponse = """
                {
                    "id": 101,
                    "name": "",
                    "email": "invalid-email",
                    "status": "unknown"
                }
                """;

        // --------------------------------------------------
        // 2. System prompt
        // --------------------------------------------------

        String systemPrompt = """
                You are an API response validation expert.

                Analyze the API response provided by the user.

                Validate every field in the API response.

                Pay special attention to:
                - empty or missing values
                - invalid email format
                - invalid or unknown status values
                - incorrect data types

                For this API:
                - id must be a number
                - name must not be empty
                - email must be a valid email address
                - status must be a valid status value
                - "unknown" is NOT a valid status value

                Return ONLY valid JSON in exactly this format:

                {
                    "valid": true or false,
                    "issues": ["issue1", "issue2"]
                }

                Do not add explanations outside the JSON.
                """;

        // --------------------------------------------------
        // 3. User prompt
        // --------------------------------------------------

        String userPrompt = """
                Analyze this API response:

                %s
                """.formatted(apiResponse);

        // --------------------------------------------------
        // 4. Send request to Ollama
        // --------------------------------------------------

        APIResponse response = ollamaClient.ask(
                systemPrompt,
                userPrompt
        );

        // --------------------------------------------------
        // 5. Validate HTTP response
        // --------------------------------------------------

        Assert.assertEquals(
                response.status(),
                200,
                "Ollama API request failed"
        );

        // --------------------------------------------------
        // 6. Parse Ollama response
        // --------------------------------------------------

        OllamaResponse ollamaResponse =
                JsonUtils.fromJson(
                        response.text(),
                        OllamaResponse.class
                );

        Assert.assertNotNull(
                ollamaResponse,
                "Ollama response should not be null"
        );

        Assert.assertNotNull(
                ollamaResponse.getMessage(),
                "Ollama message should not be null"
        );

        String aiContent =
                ollamaResponse
                        .getMessage()
                        .getContent();

        // --------------------------------------------------
        // 7. Print AI response
        // --------------------------------------------------

        System.out.println("=================================");
        System.out.println("AI ANALYSIS:");
        System.out.println("=================================");
        System.out.println(aiContent);

        // --------------------------------------------------
        // 8. Parse AI-generated JSON
        // --------------------------------------------------

        AIAnalysisResponse analysis =
                new Gson().fromJson(
                        aiContent,
                        AIAnalysisResponse.class
                );

        Assert.assertNotNull(
                analysis,
                "AI analysis should not be null"
        );

        // --------------------------------------------------
        // 9. Validate AI decision
        // --------------------------------------------------

        Assert.assertFalse(
                analysis.isValid(),
                "Invalid API response should be marked as invalid"
        );

        // --------------------------------------------------
        // 10. Validate issues
        // --------------------------------------------------

        Assert.assertNotNull(
                analysis.getIssues(),
                "AI should return validation issues"
        );

        Assert.assertFalse(
                analysis.getIssues().isEmpty(),
                "AI should identify at least one validation issue"
        );

        // Convert issues into one string so that
        // wording can vary slightly between AI responses.

        String issues = String.join(
                " ",
                analysis.getIssues()
        ).toLowerCase();

        // --------------------------------------------------
        // 11. Verify NAME issue
        // --------------------------------------------------

        Assert.assertTrue(
                issues.contains("name"),
                "AI should identify the name validation issue"
        );

        // --------------------------------------------------
        // 12. Verify EMAIL issue
        // --------------------------------------------------

        Assert.assertTrue(
                issues.contains("email"),
                "AI should identify the email validation issue"
        );

        // --------------------------------------------------
        // 13. Verify STATUS issue
        // --------------------------------------------------

        Assert.assertTrue(
                issues.contains("status"),
                "AI should identify the status validation issue"
        );

        // --------------------------------------------------
        // 14. Print individual issues
        // --------------------------------------------------

        System.out.println();
        System.out.println("Issues identified by AI:");

        for (String issue : analysis.getIssues()) {
            System.out.println("- " + issue);
        }

        // --------------------------------------------------
        // 15. Print complete Ollama response
        // --------------------------------------------------

        System.out.println();
        System.out.println("=================================");
        System.out.println("FULL OLLAMA RESPONSE:");
        System.out.println("=================================");
        System.out.println(response.text());

        // --------------------------------------------------
        // 16. Cleanup
        // --------------------------------------------------

        ollamaClient.close();
    }
}