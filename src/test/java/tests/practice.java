package tests;

import api.ApiBaseTest;
import com.microsoft.playwright.APIResponse;
import models.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.JsonUtils;

public class practice extends ApiBaseTest {

    @Test
    public void getUser() {

        APIResponse response = apiClient.get("/users/2");

        // What comes next?
        Assert.assertEquals(response.status(),200);
        User user = JsonUtils.fromJson(response.text(), User.class);

        Assert.assertEquals(user.getId(), 2);
        Assert.assertEquals(user.getName(), "Ervin Howell");
        Assert.assertEquals(user.getUsername(), "Antonette");
    }
}