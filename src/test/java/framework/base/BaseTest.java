package framework.base;

import com.microsoft.playwright.Page;
import framework.factory.PlaywrightFactory;
import framework.pages.PageManager;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseTest {

    protected Page page;
    protected PageManager pageManager;

    @BeforeMethod
    public void setUp() {

        PlaywrightFactory.initBrowser();

        page =
                PlaywrightFactory.getPage();

        pageManager =
                new PageManager(page);
    }

    @AfterMethod
    public void tearDown(
            ITestResult result) {

        if (result.getStatus()
                == ITestResult.FAILURE) {

            captureScreenshot(result);
        }

        PlaywrightFactory.closeBrowser();
    }

    private void captureScreenshot(
            ITestResult result) {

        try {

            Path screenshotDirectory =
                    Paths.get(
                            "target/screenshots"
                    );

            Files.createDirectories(
                    screenshotDirectory
            );

            String className =
                    result.getTestClass()
                            .getName();

            String testName =
                    result.getName();

            String timestamp =
                    LocalDateTime.now()
                            .format(
                                    DateTimeFormatter.ofPattern(
                                            "yyyyMMdd_HHmmss"
                                    )
                            );

            String screenshotName =
                    className.substring(
                            className.lastIndexOf('.') + 1
                    )
                            + "_"
                            + testName
                            + "_"
                            + timestamp
                            + ".png";

            Path screenshotPath =
                    screenshotDirectory.resolve(
                            screenshotName
                    );

            page.screenshot(
                    new Page.ScreenshotOptions()
                            .setPath(
                                    screenshotPath
                            )
                            .setFullPage(true)
            );

            System.out.println(
                    "Screenshot saved: "
                            + screenshotPath
            );

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}