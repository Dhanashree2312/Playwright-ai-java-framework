package tests;

import ai.OllamaClient;
import com.microsoft.playwright.APIResponse;
import ai.OllamaResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.JsonUtils;

public class OllamaTest{

    @Test
    public void askOllama(){
        OllamaClient ollamaclient= new OllamaClient();

        String systemPrompt = "You are an expert QA automation engineer. " +
                "Give practical and concise answers.";
        String userPrompt = "Explain API testing using Playwright Java.";

        APIResponse response = ollamaclient.ask(
                systemPrompt,
                userPrompt);

        Assert.assertEquals(response.status(), 200);

        OllamaResponse ollamaResponse= JsonUtils.fromJson(response.text(),OllamaResponse.class);
        String answer =
                ollamaResponse
                        .getMessage()
                        .getContent();
        System.out.println("AI Response: " + answer);
        System.out.println(response.text());
        ollamaclient.close();
    }

}
