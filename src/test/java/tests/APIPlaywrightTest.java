package tests;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class APIPlaywrightTest {

    @Test
    public void getProducts() {

        Playwright playwright = Playwright.create();

        APIRequestContext request = playwright.request()
                .newContext();

        APIResponse response = request.get(
                "https://api.practicesoftwaretesting.com/products"
        );

        System.out.println("Status Code: " + response.status());
        System.out.println("Status Text: " + response.statusText());
        System.out.println("Response Body: " + response.text());

        assertEquals(response.status(), 200);

        JsonObject responseBody = JsonParser.parseString(response.text()).getAsJsonObject();

        System.out.println("Total products: " + responseBody.get("total").getAsInt());

        System.out.println("JSON Response:");
        System.out.println(responseBody);

        playwright.close();
    }
}
