package tests;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import framework.config.ConfigReader;

import java.util.Map;

public class OllamaConnectionTest {

    public static void main(String[] args) {

        Playwright playwright = Playwright.create();

        APIRequestContext requestContext =
                playwright.request().newContext(
                        new com.microsoft.playwright.APIRequest
                                .NewContextOptions()
                                .setBaseURL(
                                        ConfigReader.get("ollama.base.url")
                                )
                                .setExtraHTTPHeaders(
                                        Map.of(
                                                "Content-Type",
                                                "application/json"
                                        )
                                )
                );

        String requestBody = """
                {
                  "model": "qwen2.5-coder:7b",
                  "messages": [
                    {
                      "role": "user",
                      "content": "Return ONLY this JSON: {\\"found\\":true}"
                    }
                  ],
                  "stream": false,
                  "options": {
                    "temperature": 0,
                    "num_predict": 20
                  }
                }
                """;

        System.out.println("Sending tiny request to Ollama...");

        long startTime = System.currentTimeMillis();

        try {

            APIResponse response =
                    requestContext.post(
                            "/api/chat",
                            RequestOptions.create()
                                    .setData(requestBody)
                                    .setTimeout(30000)
                    );

            long duration =
                    System.currentTimeMillis()
                            - startTime;

            System.out.println(
                    "Response received in "
                            + duration
                            + " ms"
            );

            System.out.println(
                    "Status: "
                            + response.status()
            );

            System.out.println(
                    "Response:"
            );

            System.out.println(
                    response.text()
            );

        } catch (Exception e) {

            long duration =
                    System.currentTimeMillis()
                            - startTime;

            System.out.println(
                    "Request failed after "
                            + duration
                            + " ms"
            );

            e.printStackTrace();

        } finally {

            requestContext.dispose();
            playwright.close();
        }
    }
}