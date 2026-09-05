package tests;

import api.ApiBaseTest;
import com.microsoft.playwright.APIResponse;
import org.testng.annotations.Test;
import utils.ResponseValidator;

public class DeleteUserTest extends ApiBaseTest {

    @Test
    public void deleteUserTest() {

        APIResponse response =
                apiClient.delete("/users/1");

        ResponseValidator.validateStatusCode(
                response,
                200
        );
    }
}