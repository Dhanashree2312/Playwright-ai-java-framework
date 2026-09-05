package tests;

import api.ApiBaseTest;
import com.microsoft.playwright.APIResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class GetUserByQueryTest extends ApiBaseTest {

    @Test
    public void getUserByQueryTest() {

        Map<String, String> queryParams =
                Map.of("username", "Bret");

        APIResponse response =
                apiClient.getWithQueryParams(
                        "/users",
                        queryParams
                );

        System.out.println("Status Code: " + response.status());
        System.out.println("Response Body:");
        System.out.println(response.text());

        Assert.assertEquals(
                response.status(),
                200,
                "Expected status code 200"
        );

        Assert.assertTrue(
                response.text().contains("\"username\": \"Bret\""),
                "Expected user Bret in response"
        );

        System.out.println("✅ Get user by query parameter test passed");
    }
}