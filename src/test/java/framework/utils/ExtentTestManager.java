package framework.utils;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {

    private static final ThreadLocal<ExtentTest> extentTest =
            new ThreadLocal<>();

    private ExtentTestManager() {
    }

    public static void startTest(String testName) {

        ExtentTest test =
                ExtentManager.getInstance()
                        .createTest(testName);

        extentTest.set(test);
    }

    public static ExtentTest getTest() {

        return extentTest.get();
    }

    public static void removeTest() {

        extentTest.remove();
    }
}