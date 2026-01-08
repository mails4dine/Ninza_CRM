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
public class CreateCampaignWithDate extends BaseClass {

	@Test
	public void createCampaignWithDateTest() throws EncryptedDocumentException, IOException {
		
	
		// 1. Data Retrieval (Using inherited utility objects from BaseClass1)
		
		String campaignName = eUtil.togetDataFromExcelFile("Campaign", 1, 2) + jUtil.togetRandomSuffix();
		String status       = eUtil.togetDataFromExcelFile("Campaign", 1, 3);
		String targetSize   = eUtil.togetDataFromExcelFile("Campaign", 1, 5);
		String closeDate    = jUtil.togetRequiredDate(31);

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
	}
}