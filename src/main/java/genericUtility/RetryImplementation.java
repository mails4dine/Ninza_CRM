package genericUtility;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

public class RetryImplementation implements IRetryAnalyzer {
    int count = 0;
    int retryLimit = 5; // Number of times to retry a failed test

    @Override
    public boolean retry(ITestResult result) {
        if (count < retryLimit) {
            count++;
       Reporter.log("Retrying test: " + result.getName() + " | Attempt: " + count, true);
            return true; // Re-run the test
        }
        return false; // Stop retrying
    }
}