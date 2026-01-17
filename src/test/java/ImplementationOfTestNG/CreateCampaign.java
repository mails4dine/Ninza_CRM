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
 * Test script to verify Campaign creation with Status and Target Size.
 * @author YourName
 */
public class CreateCampaign extends BaseClass {

	@Test
	public void createCampaign() throws EncryptedDocumentException, IOException {
		
	
		// 1. Data Retrieval (Using inherited utility objects from BaseClass1)
		
		String campaignName = eUtil.togetDataFromExcelFile("Campaign", 1, 2) + jUtil.togetRandomSuffix();		
		String targetSize   = eUtil.togetDataFromExcelFile("Campaign", 1, 5);
		

		// 2. Navigation
		HomePage homePage = new HomePage(driver);
		homePage.getCreateCampBtn().click();

		// 3. Campaign Creation
		CampaignPage campaignpage = new CampaignPage(driver);
		campaignpage.createCampaign(campaignName,targetSize);

		// 4. Validation & Toast Handling
		String actualToastText = campaignpage.getToastTextAndClose();
System.out.println(actualToastText);
		
		// Use .contains() for better stability as toast messages often have leading/trailing text
		Assert.assertTrue(actualToastText.contains(campaignName), 
			"FAILED: Campaign name [" + campaignName + "] not found in toast [" + actualToastText + "]");
		
		Reporter.log("PASS: Successfully created Campaign: " + campaignName, true);
	}
}