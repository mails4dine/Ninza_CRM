package genericUtility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
    public static ExtentReports extent; // Changed to public
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>(); // Changed to public

    public static void setupReport() {
        if (extent == null) {
            JavaUtility jUtil = new JavaUtility();
            String time = jUtil.getSystemDateInIST().replace(":", "-");
            ExtentSparkReporter spark = new ExtentSparkReporter("./ExtentReports/Report_" + time + ".html");
            
            spark.config().setDocumentTitle("Ninza CRM Execution Report");
            spark.config().setReportName("Regression Suite");
            spark.config().setTheme(Theme.STANDARD);

            extent = new ExtentReports();
            extent.attachReporter(spark);

            // Adding System Info
            extent.setSystemInfo("Project Name", "Ninza CRM");
            extent.setSystemInfo("Environment", "QA / Staging");
            extent.setSystemInfo("Platform", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("Tester Name", "Dhinesh"); 
        }
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}