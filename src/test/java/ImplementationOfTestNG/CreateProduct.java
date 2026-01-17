package ImplementationOfTestNG;

import java.io.IOException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import ObjectRepository.ProductPage;
import ObjectRepository.CampaignPage;
import genericUtility.BaseClass;


/**
 * @author YourName
 * Test Script to verify Product creation with Category, Stock, and Price details.
 */

public class CreateProduct extends BaseClass {
    @Test(description = "Verify successful creation of a product with a unique ID")
    public void createProductTest() throws IOException {
        
        // --- STEP 1: DATA RETRIEVAL ---
        String baseProductName = eUtil.togetDataFromExcelFile("Product", 1, 1);		
        String stockQuantity   = eUtil.togetDataFromExcelFile("Product", 1, 3);
        String unitPrice       = eUtil.togetDataFromExcelFile("Product", 1, 4);
        String fullproductname = baseProductName + jUtil.togetRandomNumber();

        // --- STEP 2: NAVIGATION ---
        // Using 'homepage' inherited from BaseClass1
        wUtil.waitForVisibilityofElement(driver, homepage.getProduct());
        homepage.getProduct().click();
        
System.out.println();
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
        
    }
}