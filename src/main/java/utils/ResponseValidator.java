package utils;

import com.microsoft.playwright.APIResponse;
import org.testng.Assert;

public class ResponseValidator {

    private ResponseValidator() {
        // Prevent object creation
    }

    public static void validateStatusCode(
            APIResponse response,
            int expectedStatusCode) {

        Assert.assertEquals(
                response.status(),
                expectedStatusCode,
                "Unexpected status code"
        );
    }

    public static void validateResponseContains(
            APIResponse response,
            String expectedText) {

        Assert.assertTrue(
                response.text().contains(expectedText),
                "Response does not contain: " + expectedText
        );
    }

    public static void validateHeader(
            APIResponse response,
            String headerName,
            String expectedValue) {

        String actualValue =
                response.headers().get(headerName);

        Assert.assertEquals(
                actualValue,
                expectedValue,
                "Unexpected value for header: "
                        + headerName
        );
    }
}