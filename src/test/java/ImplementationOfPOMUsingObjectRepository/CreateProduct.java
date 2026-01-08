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
import ObjectRepository.ProductPage;
import genericUtility.ExcelFileUtility;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.WebDriverUtility;


/**
 * @author YourName
 * Test Script to verify Product creation with Category, Stock, and Price details.
 */

public class CreateProduct {
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
			
		    String baseProductName = eutil.togetDataFromExcelFile("Product", 1, 1);		
	        String stockQuantity   = eutil.togetDataFromExcelFile("Product", 1, 3);
	        String unitPrice       = eutil.togetDataFromExcelFile("Product", 1, 4);
	        String fullproductname = baseProductName + jutil.togetRandomNumber();

	        // --- STEP 2: NAVIGATION ---
	        // Using 'homepage' inherited from BaseClass1
	        
	        HomePage homepage=new HomePage(driver);
	       
	        wutil.waitForVisibilityofElement(driver, homepage.getProduct());
	        homepage.getProduct().click();
	        

	        // --- STEP 3: PRODUCT DATA ENTRY ---
	        ProductPage productPage = new ProductPage(driver);
	        productPage.getAddProductBtn().click();
	        
	        // Professional approach: Filling details
	        
	        int categoryvalue=3;
	        int vendor_ID=3;
	        productPage.createProduct(fullproductname, stockQuantity, unitPrice,categoryvalue,vendor_ID);   
	        
	       
	       // Reporter.log("Product details submitted for: " + fullproductname, true);

	     // 4. Validation & Toast Handling
	            CampaignPage campaignPage = new CampaignPage(driver);
	     		String actualToastText = campaignPage.getToastTextAndClose();
	     		
	     		// Use .contains() for better stability as toast messages often have leading/trailing text
	     		Assert.assertTrue(actualToastText.contains(fullproductname), 
	     			"FAILED: Product name : [" + fullproductname + "] not found in toast [" + actualToastText + "]");
	     		
	        // --- STEP 5: FINAL REPORTING ---
	     		Reporter.log("PASS: Successfully created Product: " + fullproductname, true);
	        driver.quit();
	
  
    }
}