package ObjectRepository;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericUtility.WebDriverUtility;

public class CampaignPage {

	WebDriver driver;
	WebDriverUtility wutil=new WebDriverUtility();
	public CampaignPage(WebDriver driver) {
		
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	@FindBy(name="campaignName")
	private WebElement campaignNameTF;
	
	@FindBy(name="campaignStatus")
	private WebElement statusTF;
	
	@FindBy(name="targetSize")
	private WebElement targetTF;
		
	
	@FindBy(name="expectedCloseDate")
	private WebElement dateTF;
	
	@FindBy(xpath="//button[text()='Create Campaign']")
	private WebElement createCampSubmitBtn;
	
	@FindBy(xpath="//div[@class='Toastify__toast-body']")
	private WebElement toastmsg;
	
	@FindBy(xpath="//button[@aria-label='close']")
	private WebElement cloasemsg;
	
	@FindBy(xpath="//select[@class='form-control']")
	private WebElement selectbydropdown;
	
	@FindBy(xpath="//input[@placeholder='Search by Campaign Name']")
	private WebElement searchbytextfield;
	
	@FindBy(xpath="//i[@title='Delete']")
	private List<WebElement> deleteBtns;
	
	@FindBy(xpath="//input[@value='Delete']")
	private WebElement confirmdelete;
	
	public List<WebElement> getdeleteBtns() {
		return deleteBtns;
	}


	public WebElement getConfirmdelete() {
		return confirmdelete;
	}


	public WebElement getCampaignNameTF() {
		return campaignNameTF;
	}

	
	public WebElement getStatusTF() {
		return statusTF;
	}

	

	public WebElement getTargetTF() {
		return targetTF;
	}

	

	public WebElement getDateTF() {
		return dateTF;
	}

	

	public WebElement getCreateCampSubmitBtn() {
		return createCampSubmitBtn;
	}

	

	public WebElement getToastmsg() {
		return toastmsg;
	}

	

	public WebElement getClosemsg() {
		return cloasemsg;
	}
	
	
	public WebElement getselectbydropdown() {
		return selectbydropdown;
	}
	public WebElement getsearchbytextfield() {
		return searchbytextfield;
	}
	public void createCampaign(String campaignName, String status, String targetSize, String closeDate) {
		
		getCampaignNameTF().sendKeys(campaignName);
		getStatusTF().sendKeys(status);
		getTargetTF().clear();
		getTargetTF().sendKeys(targetSize);
		getDateTF().sendKeys(closeDate);		
		wutil.waitForVisibilityofElement(driver,createCampSubmitBtn);
		wutil.waitForPagetoLoad(driver);
		getCreateCampSubmitBtn().click();
		
		
	}
public void createCampaign(String campaignName,String targetSize) {
		
		getCampaignNameTF().sendKeys(campaignName);		
		getTargetTF().clear();
		getTargetTF().sendKeys(targetSize);				
		wutil.waitForVisibilityofElement(driver,createCampSubmitBtn);
		wutil.waitForPagetoLoad(driver);
		getCreateCampSubmitBtn().click();
		
		
	}
public void createCampaign(String campaignName, String status, String targetSize) {
	
	getCampaignNameTF().sendKeys(campaignName);	
	getStatusTF().sendKeys(status);
	getTargetTF().clear();
	getTargetTF().sendKeys(targetSize);				
	wutil.waitForVisibilityofElement(driver,createCampSubmitBtn);
	wutil.waitForPagetoLoad(driver);
	getCreateCampSubmitBtn().click();
	
	
}
	
	public void deleteCampaign(String value,String name_value) throws InterruptedException {
		wutil.waitForPagetoLoad(driver);
		wutil.waitForVisibilityofElement(driver, selectbydropdown);
		wutil.select(value, selectbydropdown);				
		wutil.waitForVisibilityofElement(driver, searchbytextfield);
		getsearchbytextfield().sendKeys(name_value);		
	}
	public String getToastTextAndClose(){
		wutil.waitForVisibilityofElement(driver,getToastmsg());
		getClosemsg().click();
			return getToastmsg().getText();
			
			
		}
	
}
