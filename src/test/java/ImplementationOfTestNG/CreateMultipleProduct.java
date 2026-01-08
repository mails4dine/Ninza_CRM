package ImplementationOfTestNG;

import java.io.IOException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import ObjectRepository.ProductPage;
import ObjectRepository.CampaignPage;
import ObjectRepository.HomePage;
import genericUtility.BaseClass;


/**
 * @author YourName
 * Test Script to verify Product creation with Category, Stock, and Price details.
 */

public class CreateMultipleProduct extends BaseClass {
   // @Test(description = "Verify successful creation of a product with a unique ID")
    public void createMultipleProductTest() throws IOException {
    	  wUtil.waitForPagetoLoad(driver);
          
          // 1. Get total rows
          int rowCount = eUtil.togetRowCount("Product");
          Reporter.log("Total data rows found: " + rowCount, true);
          
          // 2. Initialize Page Objects ONCE (Optimization)
          HomePage homepage = new HomePage(driver);
          ProductPage productPage = new ProductPage(driver);
          CampaignPage campaignPage = new CampaignPage(driver); // For shared toast locators
          
          // 3. Loop through the Excel rows
          for (int i = 1; i <= rowCount; i++) {  
        // --- STEP 1: DATA RETRIEVAL ---
        String baseProductName = eUtil.togetDataFromExcelFile("Product", i, 1);		
        String stockQuantity   = eUtil.togetDataFromExcelFile("Product", i, 3);
        String unitPrice       = eUtil.togetDataFromExcelFile("Product", i, 4);
        String fullproductname = baseProductName + jUtil.togetRandomNumber();

        // --- STEP 2: NAVIGATION ---
        // Using 'homepage' inherited from BaseClass1
        wUtil.waitForVisibilityofElement(driver, homepage.getProduct());
        homepage.getProduct().click();
        

        // --- STEP 3: PRODUCT DATA ENTRY ---       
        productPage.getAddProductBtn().click();
        
        // Professional approach: Filling details
        
        int categoryvalue=3;
        int vendor_ID=3;
        productPage.createProduct(fullproductname, stockQuantity, unitPrice,categoryvalue,vendor_ID);   
        
       
       // Reporter.log("Product details submitted for: " + fullproductname, true);

     // 4. Validation & Toast Handling
           
     		String actualToastText = campaignPage.getToastTextAndClose();
     		
     		// Use .contains() for better stability as toast messages often have leading/trailing text
     		Assert.assertTrue(actualToastText.contains(fullproductname), 
     			"FAILED: Product name : [" + fullproductname + "] not found in toast [" + actualToastText + "]");
     		
        // --- STEP 5: FINAL REPORTING ---
     		Reporter.log("PASS: Successfully created Product: " + fullproductname, true);
        
    }
    }
}