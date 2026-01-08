package ImplementationOfPOMUsingObjectRepository;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import ObjectRepository.LoginPage;
import genericUtility.PropertyFileUtility;
import genericUtility.WebDriverUtility;

public class UserAuthentication{

	public static void main(String[] args) throws IOException, InterruptedException {
		WebDriverUtility wutil=new WebDriverUtility();
		 PropertyFileUtility putil=new PropertyFileUtility();		
	       String BROWSER=putil.togetDataFrompropertiesFile("Browser");
			String URL=putil.togetDataFrompropertiesFile("Url");
			String USERNAME=putil.togetDataFrompropertiesFile("Username");
			String PASSWORD=putil.togetDataFrompropertiesFile("Password");
			
			
			WebDriver driver=null;
			if(BROWSER.equals("Chrome"))
			{
				ChromeOptions options = new ChromeOptions();

				Map<String, Object> prefs = new HashMap<>();
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_leak_detection", false);	
				options.setExperimentalOption("prefs", prefs);
				driver=new ChromeDriver(options);
			}
			
			else if(BROWSER.equals("Edge"))
			{
				new EdgeDriver();
			}
			else if(BROWSER.equals("Firefox"))
			{
				new FirefoxDriver();
			}
			
			driver.manage().window().maximize();
			wutil.waitForPagetoLoad(driver);
			driver.get(URL);	
			LoginPage loginpage=new LoginPage(driver);
			loginpage.loginToApplication(USERNAME, PASSWORD);
		
		 wutil.waitForUrlToContain(driver, "dashboard");
	        String currentUrl = driver.getCurrentUrl();
	        Assert.assertTrue(currentUrl.contains("dashboard"), "Login Failed: Dashboard not reached.");
	        Reporter.log("Login Successfully", true);
	        Reporter.log("URL :"+currentUrl,true);
	       // Reporter.log("===============================================", true);  
	        driver.quit();
	}

}
