package tests;

import api.ApiBaseTest;
import com.microsoft.playwright.APIResponse;
import models.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.JsonUtils;
import utils.ResponseValidator;

public class UpdateUserTest extends ApiBaseTest {

    @Test
    public void updateUserTest() {

        User user = new User(
                1,
                "Updated Dhanashree",
                "dhanashree",
                "updated@test.com"
        );

        String requestBody =
                JsonUtils.toJson(user);

        APIResponse response =
                apiClient.put(
                        "/users/1",
                        requestBody
                );

        ResponseValidator.validateStatusCode(
                response,
                200
        );

        User responseUser =
                JsonUtils.fromJson(
                        response.text(),
                        User.class
                );

        Assert.assertEquals(
                responseUser.getName(),
                "Updated Dhanashree"
        );

        Assert.assertEquals(
                responseUser.getEmail(),
                "updated@test.com"
        );
    }
}