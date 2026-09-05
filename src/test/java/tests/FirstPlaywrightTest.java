package tests;

import framework.base.BaseTest;
import framework.utils.ExcelReader;
import framework.utils.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FirstPlaywrightTest extends BaseTest {

    @Test
    public void verifyHomePage() {

        pageManager.getHomePage().navigate();

        Assert.assertEquals(
                pageManager.getHomePage().getTitle(),
                "Practice Software Testing - Toolshop - v5.0"
        );
    }

    @Test
    public void verifyHomePageUrl() {

        pageManager.getHomePage().navigate();

        Assert.assertEquals(
                pageManager.getHomePage().getUrl(),
                "https://practicesoftwaretesting.com/"
        );
        Assert.assertEquals(pageManager.getHomePage().getTitle(),"Practice Software Testing - Toolshop - v5.0");
    }

    @Test(dataProvider = "loginData", dataProviderClass = TestDataProvider.class)
    public void justTest(String email, String password) {
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
    }

    @Test
    public void checkExcelSize() {

        ExcelReader reader = new ExcelReader();

        System.out.println("Rows: " + reader.getRowCount());
        System.out.println("Columns: " + reader.getColumnCount());
    }
}