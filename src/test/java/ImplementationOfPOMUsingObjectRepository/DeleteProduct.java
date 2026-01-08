package ImplementationOfPOMUsingObjectRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import org.testng.Assert;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import ObjectRepository.CampaignPage;
import ObjectRepository.LoginPage;
import ObjectRepository.ProductPage;
import genericUtility.PropertyFileUtility;
import genericUtility.WebDriverUtility;

public class DeleteProduct {

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
			
				wutil.waitForPagetoLoad(driver);  
			 	ProductPage productpage=new ProductPage(driver);  
			 	CampaignPage campaignpage=new CampaignPage(driver);
			 	String value="Search by Product Name";
			 	String name_value="Zukebar"; 	
			 	productpage.deleteProduct(value, name_value);	    		
			 	wutil.waitForPagetoLoad(driver);   
			 	try {
			 		wutil.waitForVisibilityofElement(driver, productpage.getDeleteBtns().get(0));
			    } catch (Exception e) {
			        Reporter.log("No products found for search criteria: " + name_value, true);
			        return; 
			    }
			 	int count = 0;     
			      while (true) {
			     	 try {   				
			     		wutil.setImplicitWait(driver, 0);
			          	 List<WebElement> buttons = productpage.getDeleteBtns();	                
			              boolean isFinished = buttons.isEmpty();
			              wutil.setImplicitWait(driver, 30);

			              if (isFinished) {
			                  Reporter.log("Cleanup complete. No more items found.", true);
			                  break;
			              }
			              wutil.waitForVisibilityofElement(driver,productpage.getDeleteBtns().get(0));
			              productpage.getDeleteBtns().get(0).click();
			           
			              wutil.waitForVisibilityofElement(driver, productpage.getConfirmdelete());
			              productpage.getConfirmdelete().click();               
			              count++;                             
			             campaignpage.getToastTextAndClose();            


			          } catch (StaleElementReferenceException e) {
			              
			              continue; 
			          } catch (Exception e) {
			              Reporter.log("Stopping cleanup due to: " + e.getMessage(), true);
			              break;
			          }
			      }
			     Reporter.log("Total :"+count + "Product's Deleted Successfully ", true);
			     driver.quit();
	}

   
 
}
