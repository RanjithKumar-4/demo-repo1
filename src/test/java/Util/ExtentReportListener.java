package Util;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import Util.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;

public class ExtentReportListener implements ITestListener {

    private ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {

        ExtentSparkReporter spark =
                new ExtentSparkReporter(
                        System.getProperty("user.dir") + "/reports/ExtentReport.html");

        extent = new ExtentReports();
        extent.attachReporter(spark);


        String browserName = (String) context.getAttribute("browser");

        extent.setSystemInfo("Computer Name", "Localhost");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tester Name", "Saran K");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo(
                "Browser",
                browserName != null ? browserName.toUpperCase() : "UNKNOWN"
        );
    }

    @Override
    public void onTestStart(ITestResult result) {
        test.set(extent.createTest(result.getName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Passed");
        attachScreenshot(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, result.getThrowable());
        attachScreenshot(result);
    }

    private void attachScreenshot(ITestResult result) {
        try {
            WebDriver driver =
                    (WebDriver) result.getTestContext().getAttribute("driver");

            String path =
                    ScreenshotUtil.takeScreenshot(driver, result.getName());


            if (path != null && new File(path).exists()) {
                test.get().addScreenCaptureFromPath(path);
            } else {
                test.get().log(Status.INFO, "Screenshot not available");
            }

        } catch (Exception e) {
            test.get().log(Status.INFO,
                    "Screenshot attachment skipped: " + e.getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
