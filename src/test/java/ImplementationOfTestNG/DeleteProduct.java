package ImplementationOfTestNG;


import java.util.List;


import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.Test;
import ObjectRepository.CampaignPage;
import ObjectRepository.ProductPage;
import genericUtility.BaseClass;

public class DeleteProduct extends BaseClass{

	@Test
    public void deleteProductTest() throws InterruptedException  {
	wUtil.waitForPagetoLoad(driver);  
 	ProductPage productpage=new ProductPage(driver);  
 	CampaignPage campaignpage=new CampaignPage(driver);
 	String value="Search by Product Name";
 	String name_value="Zukebar"; 	
 	productpage.deleteProduct(value, name_value);	    		
 	wUtil.waitForPagetoLoad(driver);   
 	try {
        wUtil.waitForVisibilityofElement(driver, productpage.getDeleteBtns().get(0));
    } catch (Exception e) {
        Reporter.log("No products found for search criteria: " + name_value, true);
        return; 
    }
 	int count = 0;     
      while (true) {
     	 try {   				
     		wUtil.setImplicitWait(driver, 0);
          	 List<WebElement> buttons = productpage.getDeleteBtns();	                
              boolean isFinished = buttons.isEmpty();
              wUtil.setImplicitWait(driver, 30);

              if (isFinished) {
                  Reporter.log("Cleanup complete. No more items found.", true);
                  break;
              }
              wUtil.waitForVisibilityofElement(driver,productpage.getDeleteBtns().get(0));
              productpage.getDeleteBtns().get(0).click();
           
              wUtil.waitForVisibilityofElement(driver, productpage.getConfirmdelete());
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
 }
}
