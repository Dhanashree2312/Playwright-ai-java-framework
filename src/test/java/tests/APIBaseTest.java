package tests;

import api.APIClient;
import com.microsoft.playwright.APIResponse;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class APIBaseTest {

    protected APIClient apiClient;

    @BeforeClass
    public void setUpAPI() {

        apiClient =
                new APIClient();
    }

    protected void assertStatusCode(
            APIResponse response,
            int expectedStatusCode) {

        Assert.assertEquals(
                response.status(),
                expectedStatusCode,
                "Unexpected status code. Expected: "
                        + expectedStatusCode
                        + " but received: "
                        + response.status()
        );
    }

    protected void assertContentType(
            APIResponse response,
            String expectedContentType) {

        String contentType =
                response.headers()
                        .get("content-type");

        Assert.assertNotNull(
                contentType,
                "Content-Type header is missing"
        );

        Assert.assertTrue(
                contentType.contains(
                        expectedContentType
                ),
                "Expected Content-Type to contain: "
                        + expectedContentType
                        + " but was: "
                        + contentType
        );
    }

    @AfterClass
    public void tearDownAPI() {

        if (apiClient != null) {

            apiClient.close();
        }
    }
}