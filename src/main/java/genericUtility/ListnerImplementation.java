package genericUtility;

import org.testng.IConfigurationListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ListnerImplementation implements ITestListener, IConfigurationListener {

	@Override
	public void onStart(ITestContext context) {
		// Initializes the report and the System Info (Tester Name, Project, etc.)
		ExtentReportManager.setupReport();
		Reporter.log(">>> Execution Started: " + context.getName(), true);
	}

	@Override
	public void onTestStart(ITestResult result) {
		// Get Method Name
		String testName = result.getMethod().getMethodName();
		
		// Create test in report and assign it to ThreadLocal for thread-safety
		ExtentTest t = ExtentReportManager.extent.createTest(testName);
		ExtentReportManager.test.set(t);
		
		ExtentReportManager.test.get().log(Status.INFO, testName + " -> Started");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentReportManager.test.get().log(Status.PASS, testName + " -> Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		
		// Capture Exception
		ExtentReportManager.test.get().log(Status.FAIL, testName + " -> Failed");
		ExtentReportManager.test.get().log(Status.FAIL, result.getThrowable());
		
		// Optional: Attach Screenshot (Uncomment if BaseClass.sDriver is accessible)
		/*
		try {
			String path = new WebDriverUtility().takeScreenShot(BaseClass.sDriver, testName);
			ExtentReportManager.test.get().addScreenCaptureFromPath(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentReportManager.test.get().log(Status.SKIP, result.getMethod().getMethodName() + " -> Skipped");
		ExtentReportManager.test.get().log(Status.SKIP, result.getThrowable());
	}

	@Override
	public void onFinish(ITestContext context) {
		// Mandatory: This writes all System Info and Test logs into the HTML file
		ExtentReportManager.flush();
		Reporter.log(">>> Execution Finished: " + context.getName(), true);
	}

	@Override
	public void onConfigurationSuccess(ITestResult itr) {
		if (itr.getMethod().getMethodName().contains("configAM")) {
			Reporter.log("--- Logout Verified for: " + itr.getName() + " ---", true);
		}
	}
}
/*
 * public class ListnerImplementation implements ITestListener,
 * IConfigurationListener { long startTime;
 * 
 * @Override public void onConfigurationSuccess(ITestResult itr) { if
 * (itr.getMethod().getMethodName().contains("configAM")) {
 * Reporter.log("--- Logout Verified for: " + itr.getName() + " ---", true); } }
 * 
 * @Override public void onTestStart(ITestResult result) {
 * Reporter.log("--- Execution Started: " + result.getMethod().getMethodName() +
 * " ---", true); }
 * 
 * @Override public void onTestSuccess(ITestResult result) { // This happens
 * right at the end of the test. // It is the most reliable place to put a
 * 'Logout' confirmation log. Reporter.log("--- Test Passed: " +
 * result.getName() + " ---", true);
 * Reporter.log("Post-Test Verification: Checking Session termination...",
 * true); }
 * 
 * 
 * @Override public void onTestFailure(ITestResult result) { String testName =
 * result.getMethod().getMethodName(); Reporter.log("--- Test Failed: " +
 * testName + " ---", true);
 * 
 * try { // 1. Capture the driver from the BaseClass instance WebDriver driver =
 * (WebDriver)
 * result.getTestClass().getRealClass().getField("driver").get(result.
 * getInstance());
 * 
 * // 2. Take the screenshot using your utility WebDriverUtility wUtil = new
 * WebDriverUtility(); // Ensure the method name matches your JavaUtility method
 * String date = new JavaUtility().getSystemDateInIST(); String path =
 * wUtil.takeScreenshot(driver, testName + "_" + date);
 * 
 * // 3. Attach to the TestNG Report (adds a clickable link and a small preview)
 * Reporter.log("<a href='" + path + "'> <img src='" + path +
 * "' height='100' width='100'/> </a>", true);
 * 
 * } catch (Exception e) { Reporter.log("Failed to capture screenshot: " +
 * e.getMessage(), true); } }
 * 
 * @Override public void onStart(ITestContext context) { startTime =
 * System.currentTimeMillis(); // Capture start time
 * Reporter.log(">>> Starting Suite: " + context.getName(), true); }
 * 
 * 
 * @Override public void onFinish(ITestContext context) { long endTime =
 * System.currentTimeMillis(); long totalTime = endTime - startTime; long
 * seconds = (totalTime / 1000) % 60; long minutes = (totalTime / (1000 * 60)) %
 * 60;
 * 
 * // Using <h3> and <pre> tags to force a new, formatted section in the HTML
 * report String summary = "<h3>FINAL SUITE SUMMARY: " + context.getName() +
 * "</h3>" + "<pre>" + "Total Tests Run: " + context.getAllTestMethods().length
 * + "\n" + "Passed:          " + context.getPassedTests().size() + "\n" +
 * "Failed:          " + context.getFailedTests().size() + "\n" +
 * "Skipped/Retry:   " + context.getSkippedTests().size() + "\n" +
 * "Execution Time:  " + minutes + "m " + seconds + "s" + "</pre>";
 * 
 * Reporter.log(summary, true); }
 */