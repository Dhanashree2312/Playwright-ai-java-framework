package ai;

import com.google.gson.Gson;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import framework.config.ConfigReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OllamaClient {

    private final Playwright playwright;
    private final APIRequestContext apiRequestContext;
    private final Gson gson;

    public OllamaClient() {

        playwright = Playwright.create();

        apiRequestContext =
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

        gson = new Gson();
    }

    public APIResponse ask(
            String systemPrompt,
            String userPrompt) {

        // System message
        Map<String, Object> systemMessage =
                new HashMap<>();

        systemMessage.put(
                "role",
                "system"
        );

        systemMessage.put(
                "content",
                systemPrompt
        );

        // User message
        Map<String, Object> userMessage =
                new HashMap<>();

        userMessage.put(
                "role",
                "user"
        );

        userMessage.put(
                "content",
                userPrompt
        );

        // Request body
        Map<String, Object> requestBody =
                new HashMap<>();

        requestBody.put(
                "model",
                ConfigReader.get("ollama.model")
        );

        requestBody.put(
                "messages",
                List.of(
                        systemMessage,
                        userMessage
                )
        );

        // Return one complete response
        requestBody.put(
                "stream",
                false
        );

        // AI generation controls
        Map<String, Object> options =
                new HashMap<>();

        // Deterministic output
        options.put(
                "temperature",
                0
        );

        /*
         * Qwen 7B is running locally on the machine.
         * Keep the generated response small so that
         * the API can complete within our timeout.
         */
        options.put(
                "num_predict",
                350
        );

        requestBody.put(
                "options",
                options
        );

        String jsonRequestBody =
                gson.toJson(requestBody);

        System.out.println();
        System.out.println(
                "================================="
        );
        System.out.println(
                "SENDING REQUEST TO OLLAMA"
        );
        System.out.println(
                "================================="
        );

        System.out.println(
                "Model: "
                        + ConfigReader.get("ollama.model")
        );

        System.out.println(
                "Max generated tokens: 350"
        );

        return apiRequestContext.post(
                "/api/chat",
                RequestOptions.create()
                        .setHeader(
                                "Content-Type",
                                "application/json"
                        )
                        .setData(
                                jsonRequestBody
                        )
                        .setTimeout(60000)
        );
    }

    public void close() {

        if (apiRequestContext != null) {
            apiRequestContext.dispose();
        }

        if (playwright != null) {
            playwright.close();
        }
    }
}