package listeners;

import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import framework.utils.ExtentManager;
import framework.utils.ExtentTestManager;

public class ExtentTestListener
        implements ITestListener {

    @Override
    public void onStart(ITestContext context) {

        System.out.println(
                "======================================"
        );

        System.out.println(
                "Test Suite Started: "
                        + context.getName()
        );

        System.out.println(
                "======================================"
        );
    }

    @Override
    public void onTestStart(ITestResult result) {

        String testName =
                result.getMethod()
                        .getMethodName();

        ExtentTestManager.startTest(
                testName
        );

        ExtentTestManager.getTest()
                .log(
                        Status.INFO,
                        "Test Started"
                );
    }

    @Override
    public void onTestSuccess(
            ITestResult result) {

        ExtentTestManager.getTest()
                .log(
                        Status.PASS,
                        "Test Passed"
                );

        ExtentTestManager.removeTest();
    }

    @Override
    public void onTestFailure(
            ITestResult result) {

        ExtentTestManager.getTest()
                .log(
                        Status.FAIL,
                        "Test Failed"
                );

        if (result.getThrowable() != null) {

            ExtentTestManager.getTest()
                    .log(
                            Status.FAIL,
                            result.getThrowable()
                    );
        }

        ExtentTestManager.removeTest();
    }

    @Override
    public void onTestSkipped(
            ITestResult result) {

        ExtentTestManager.getTest()
                .log(
                        Status.SKIP,
                        "Test Skipped"
                );

        ExtentTestManager.removeTest();
    }

    @Override
    public void onFinish(
            ITestContext context) {

        ExtentManager.flush();

        System.out.println(
                "======================================"
        );

        System.out.println(
                "Test Suite Finished"
        );

        System.out.println(
                "Extent Report Generated"
        );

        System.out.println(
                "======================================"
        );
    }
}