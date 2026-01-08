package ImplementationOfTestNG;



import java.util.List;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.Test;
import ObjectRepository.CampaignPage;
import genericUtility.BaseClass;



public class DeleteCampaigns extends BaseClass {

    @Test
    public void deleteCampaignsTest() throws InterruptedException  {
        
    	wUtil.waitForPagetoLoad(driver);    	
    	CampaignPage campaignpage=new CampaignPage(driver);    	
    	String value="Search by Campaign Name";
    	String name_value="JoseJohn";
    	campaignpage.deleteCampaign(value,name_value);     	
    	wUtil.waitForPagetoLoad(driver);    
    	try {
            wUtil.waitForVisibilityofElement(driver, campaignpage.getdeleteBtns().get(0));
        } catch (Exception e) {
            Reporter.log("No products found for search criteria: " + name_value, true);
            return; 
        }
    	int count = 0; 

        while (true) {
            try {   				
            	wUtil.setImplicitWait(driver, 0);
                List<WebElement> buttons = campaignpage.getdeleteBtns();
                boolean isFinished = buttons.isEmpty();
                wUtil.setImplicitWait(driver, 30); // Restore standard wait

                if (isFinished) {
                    Reporter.log("Cleanup complete. No more items found.", true);
                    break;
                }
                wUtil.waitForVisibilityofElement(driver,campaignpage.getdeleteBtns().get(0));
                campaignpage.getdeleteBtns().get(0).click();
             
                wUtil.waitForVisibilityofElement(driver, campaignpage.getConfirmdelete());
                campaignpage.getConfirmdelete().click();               
                count++;                             
                campaignpage.getToastTextAndClose();             


            } catch (StaleElementReferenceException e) {
                
                continue; 
            } catch (Exception e) {
                Reporter.log("Stopping cleanup due to: " + e.getMessage(), true);
                break;
            }
        }
        Reporter.log("Total items deleted: " + count, true);
    }
}