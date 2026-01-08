package genericUtility;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.ITestResult;
import ObjectRepository.HomePage;
import ObjectRepository.LoginPage;

/**
 * BaseClass contains common configurations for Browser Setup, App Login/Logout, 
 * and Report Generation. All Test Scripts should extend this class.
 */
@Listeners(genericUtility.ListnerImplementation.class)
public class BaseClass {
	 public WebDriver driver;   
    public PropertyFileUtility pUtil = new PropertyFileUtility();
    public WebDriverUtility wUtil = new WebDriverUtility();
    public ExcelFileUtility eUtil=new ExcelFileUtility();
    public JavaUtility jUtil = new JavaUtility();
    public LoginPage loginpage;
    public HomePage homepage;

    @BeforeSuite
    public void configBS() {
        System.out.println(">>> Initializing Suite:");
        
    }

    @BeforeClass(alwaysRun = true)
    public void configBC() throws IOException {
    	
    	//launch browser
        String browser = pUtil.togetDataFrompropertiesFile("Browser");
        
        if (browser.equalsIgnoreCase("chrome")) {
        	ChromeOptions options = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_leak_detection", false);
            options.setExperimentalOption("prefs", prefs);
            options.addArguments("--remote-allow-origins=*");          
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else {
            driver = new EdgeDriver();
        }
        
        driver.manage().window().maximize();
        wUtil.waitForPagetoLoad(driver);
        loginpage = new LoginPage(driver);
        homepage = new HomePage(driver);
    }

    @BeforeMethod(alwaysRun = true)
    public void configBM() throws IOException, InterruptedException {
        String url = pUtil.togetDataFrompropertiesFile("Url");
        String un  = pUtil.togetDataFrompropertiesFile("Username");
        String pwd = pUtil.togetDataFrompropertiesFile("Password");

        driver.get(url);
       loginpage.loginToApplication(un, pwd);
        
        System.out.println(">>> Logged in successfully.");
    }

    @AfterMethod(alwaysRun = true)
    public void configAM(ITestResult result) { // Pass ITestResult here
        try {
            homepage.logoutToApplication();
            System.out.println(">>> Logged out successfully.");
        } catch (Exception e) {
            System.err.println(">>> Logout failed: " + e.getMessage());
        }
        
        // ATTACH LOGS TO THE CURRENT TEST RESULT
        Reporter.setCurrentTestResult(result); 
        
        String currenturl = driver.getCurrentUrl(); 
        Assert.assertTrue(currenturl.endsWith("/"), "Logout Failed: Not redirected to Login page.");
        
        Reporter.log("Logout Successfully", true);
        Reporter.log("URL :" + currenturl, true);
        Reporter.log("===============================================", true);
    }

    @AfterClass(alwaysRun = true)
    public void configAC() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void configAS() {
    	System.out.println("Suite Execution Completed");
    }

   
}