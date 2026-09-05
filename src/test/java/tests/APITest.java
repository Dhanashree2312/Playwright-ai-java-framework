package tests;

import com.microsoft.playwright.APIResponse;
import api.APIClient;
import models.User;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.JsonUtils;

import java.util.Map;

public class APITest {

    private APIClient apiClient;

    @BeforeClass
    public void setUp() {

        apiClient = new APIClient();
    }

    @Test
    public void getUserWithQueryParameterTest() {

        Map<String, String> queryParams =
                Map.of(
                        "username",
                        "Bret"
                );

        APIResponse response =
                apiClient.getWithQueryParams(
                        "/users",
                        queryParams
                );

        System.out.println(
                "Status Code: " + response.status()
        );

        System.out.println(
                "Response Body:"
        );

        System.out.println(
                response.text()
        );

        Assert.assertEquals(
                response.status(),
                200,
                "Expected status code 200"
        );

        System.out.println(
                "✅ Query parameter test passed"
        );
    }

    @AfterClass
    public void tearDown() {

        apiClient.close();
    }
}