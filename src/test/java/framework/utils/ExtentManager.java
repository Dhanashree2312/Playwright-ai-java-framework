package framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extentReports;

    private ExtentManager() {
    }

    public static synchronized ExtentReports getInstance() {

        if (extentReports == null) {

            String reportPath =
                    System.getProperty("user.dir")
                            + "/target/extent-report.html";

            ExtentSparkReporter reporter =
                    new ExtentSparkReporter(reportPath);

            reporter.config()
                    .setDocumentTitle("Automation Test Report");

            reporter.config()
                    .setReportName(
                            "Playwright Automation Framework"
                    );

            extentReports =
                    new ExtentReports();

            extentReports.attachReporter(reporter);

            extentReports.setSystemInfo(
                    "Framework",
                    "Playwright Java"
            );

            extentReports.setSystemInfo(
                    "Test Framework",
                    "TestNG"
            );

            extentReports.setSystemInfo(
                    "Language",
                    "Java"
            );
        }

        return extentReports;
    }

    public static synchronized void flush() {

        if (extentReports != null) {
            extentReports.flush();
        }
    }
}