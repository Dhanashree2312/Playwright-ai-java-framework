package tests;

import api.ApiBaseTest;
import com.microsoft.playwright.APIResponse;
import models.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.JsonUtils;
import utils.ResponseValidator;

public class CreateUserTest extends ApiBaseTest {

    @Test
    public void createUserTest() {

        User user = new User(
                0,
                "Dhanashree",
                "dhanashree",
                "dhanashree@test.com"
        );

        // Java → JSON
        String requestBody =
                JsonUtils.toJson(user);

        APIResponse response =
                apiClient.post(
                        "/users",
                        requestBody
                );

        ResponseValidator.validateStatusCode(
                response,
                201
        );

        // JSON → Java
        User responseUser =
                JsonUtils.fromJson(
                        response.text(),
                        User.class
                );

        Assert.assertEquals(
                responseUser.getName(),
                "Dhanashree"
        );

        Assert.assertEquals(
                responseUser.getUsername(),
                "dhanashree"
        );

        Assert.assertEquals(
                responseUser.getEmail(),
                "dhanashree@test.com"
        );
    }
}