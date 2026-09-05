package tests;

import api.ApiBaseTest;
import com.microsoft.playwright.APIResponse;
import models.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.JsonUtils;
import utils.ResponseValidator;

public class UserApiTest extends ApiBaseTest {

    @Test
    public void getUserTest() {

        APIResponse response =
                apiClient.get("/users/1");

        ResponseValidator.validateStatusCode(
                response,
                200
        );

        User user =
                JsonUtils.fromJson(
                        response.text(),
                        User.class
                );

        Assert.assertEquals(user.getId(), 1);

        Assert.assertEquals(
                user.getName(),
                "Leanne Graham"
        );

        Assert.assertEquals(
                user.getUsername(),
                "Bret"
        );
    }
}