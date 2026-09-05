package ai;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.playwright.APIResponse;

public class TestCaseGenerator {

    private final OllamaClient ollamaClient;
    private final Gson gson;

    public TestCaseGenerator() {

        ollamaClient =
                new OllamaClient();

        gson =
                new GsonBuilder()
                        .setPrettyPrinting()
                        .create();
    }

    public TestCaseResponse generateTestCases(
            String requirement) {

        String systemPrompt = """
                You are a software test engineer.

                Generate exactly 1 test case for the requirement.

                Keep it extremely concise.

                The test case must contain:
                - testCaseId
                - title
                - description
                - priority
                - preconditions
                - testSteps
                - expectedResult

                Use only:
                - 1 precondition
                - 2 test steps
                - 1 short expected result

                Return ONLY valid JSON.
                Do not return Markdown.
                Do not provide explanations.

                Use exactly this structure:

                {
                  "testCases": [
                    {
                      "testCaseId": "TC001",
                      "title": "Valid login",
                      "description": "Verify valid login",
                      "priority": "High",
                      "preconditions": [
                        "Registered user exists"
                      ],
                      "testSteps": [
                        "Enter valid credentials",
                        "Click Login"
                      ],
                      "expectedResult": "User is logged in"
                    }
                  ]
                }
                """;

        String userPrompt =
                """
                Generate exactly 1 concise test case
                for this requirement:

                %s
                """.formatted(
                        requirement
                );

        APIResponse response =
                ollamaClient.ask(
                        systemPrompt,
                        userPrompt
                );

        if (response.status() != 200) {

            throw new RuntimeException(
                    "Ollama request failed. Status code: "
                            + response.status()
                            + "\nResponse: "
                            + response.text()
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

        String generatedContent =
                ollamaResponse
                        .getMessage()
                        .getContent();

        if (generatedContent == null
                || generatedContent.isBlank()) {

            throw new RuntimeException(
                    "Ollama returned empty AI content"
            );
        }

        System.out.println();
        System.out.println(
                "================================="
        );
        System.out.println(
                "RAW AI RESPONSE"
        );
        System.out.println(
                "================================="
        );

        System.out.println(
                generatedContent
        );

        generatedContent =
                cleanJsonResponse(
                        generatedContent
                );

        System.out.println();
        System.out.println(
                "================================="
        );
        System.out.println(
                "CLEANED AI JSON"
        );
        System.out.println(
                "================================="
        );

        System.out.println(
                generatedContent
        );

        try {

            TestCaseResponse result =
                    gson.fromJson(
                            generatedContent,
                            TestCaseResponse.class
                    );

            if (result == null
                    || result.getTestCases() == null
                    || result.getTestCases().isEmpty()) {

                throw new RuntimeException(
                        "AI response does not contain test cases"
                );
            }

            return result;

        } catch (Exception e) {

            throw new RuntimeException(
                    "AI did not return valid test case JSON.\n"
                            + "AI response:\n"
                            + generatedContent,
                    e
            );
        }
    }

    private String cleanJsonResponse(
            String response) {

        response =
                response.trim();

        if (response.startsWith("```json")) {

            response =
                    response.substring(7);

        } else if (response.startsWith("```")) {

            response =
                    response.substring(3);
        }

        if (response.endsWith("```")) {

            response =
                    response.substring(
                            0,
                            response.length() - 3
                    );
        }

        return response.trim();
    }

    public void close() {

        if (ollamaClient != null) {
            ollamaClient.close();
        }
    }
}