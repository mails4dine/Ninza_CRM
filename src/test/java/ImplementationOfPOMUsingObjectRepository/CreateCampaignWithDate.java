package ImplementationOfPOMUsingObjectRepository;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import ObjectRepository.CampaignPage;
import ObjectRepository.HomePage;
import ObjectRepository.LoginPage;
import genericUtility.ExcelFileUtility;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.WebDriverUtility;



/**
 * Test script to verify Campaign creation with Status, Target Size, and Close Date.
 * @author YourName
 */
public class CreateCampaignWithDate  {

	public static void main(String[] args) throws InterruptedException, EncryptedDocumentException, IOException {
		 WebDriverUtility wutil=new WebDriverUtility();
		 PropertyFileUtility putil=new PropertyFileUtility();
		 JavaUtility jutil=new JavaUtility();
		 ExcelFileUtility eutil=new ExcelFileUtility();
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
		
				String campaignName = eutil.togetDataFromExcelFile("Campaign", 1, 2) + jutil.togetRandomSuffix();
				String status       = eutil.togetDataFromExcelFile("Campaign", 1, 3);
				String targetSize   = eutil.togetDataFromExcelFile("Campaign", 1, 5);
				String closeDate    = jutil.togetRequiredDate(31);

				// 2. Navigation
				HomePage homePage = new HomePage(driver);
				homePage.getCreateCampBtn().click();

				// 3. Campaign Creation
				CampaignPage campaignpage = new CampaignPage(driver);
				campaignpage.createCampaign(campaignName, status, targetSize, closeDate);

				// 4. Validation & Toast Handling
				String actualToastText = campaignpage.getToastTextAndClose();
				
				// Use .contains() for better stability as toast messages often have leading/trailing text
				Assert.assertTrue(actualToastText.contains(campaignName), 
					"FAILED: Campaign name [" + campaignName + "] not found in toast [" + actualToastText + "]");
				
				Reporter.log("PASS: Successfully created Campaign: " + campaignName, true);
		driver.quit();
	}
	
}