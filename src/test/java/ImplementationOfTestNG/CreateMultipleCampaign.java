package ImplementationOfTestNG;


import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import ObjectRepository.CampaignPage;
import ObjectRepository.HomePage;
import genericUtility.BaseClass;



/**
 * Test script to verify Campaign creation with Status, Target Size, and Close Date.
 * @author YourName
 */
public class CreateMultipleCampaign extends BaseClass {

	
	public void createMultipleCampaignTest() throws EncryptedDocumentException, IOException {
		
		    wUtil.waitForPagetoLoad(driver);
	        
	        // 1. Get total row count
	        int rowCount = eUtil.togetRowCount("Campaign");
	        Reporter.log("Total data rows found: " + rowCount, true);
	        
	        // 2. Initialize Page Objects ONCE (Optimization: Move outside the loop)
	        HomePage homePage = new HomePage(driver);
	        CampaignPage campaignpage = new CampaignPage(driver);
	
	        // 3. Loop through the Excel rows
	        
	        for (int i = 1; i <= rowCount; i++) {		
				String campaignName = eUtil.togetDataFromExcelFile("Campaign", i, 2) + jUtil.togetRandomSuffix();
				//String status       = eUtil.togetDataFromExcelFile("Campaign", i, 3);
				String targetSize   = eUtil.togetDataFromExcelFile("Campaign", i, 5);
				//String closeDate    = jUtil.togetRequiredDate(31);

		// 2. Navigation
		
		homePage.getCreateCampBtn().click();

		// 3. Campaign Creation
		
		campaignpage.createCampaign(campaignName, targetSize);

		// 4. Validation & Toast Handling
		String actualToastText = campaignpage.getToastTextAndClose();
		
		// 3. Perform Assertion
       
		Assert.assertTrue(actualToastText.contains(campaignName), 
			"FAILED: Campaign name [" + campaignName + "] not found in toast [" + actualToastText + "]");
		
		Reporter.log("PASS: Successfully created Campaign: " + campaignName, true);
		Reporter.log("===============================================", true);
	}
}
}